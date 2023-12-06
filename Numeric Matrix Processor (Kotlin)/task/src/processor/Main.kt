package processor

import kotlin.math.*


interface NMP {
    fun matrix(n1:Int,n2:Int):List<MutableList<Double>> {
        val list = mutableListOf(
            mutableListOf<Double>()
        )
        for (i in 0 until n1) {
            val ml = mutableListOf<Double>()
            readln().split(" ").map { it.toDouble() }.take(n2).forEach {
                    num ->
                ml.add(num)
            }
            list.add(ml)
        }
        return list.drop(1)
    }
    infix fun List<MutableList<Double>>.mulMatrix(factor:Double):  List<List<Double>>  {
        return this.map { ints ->
            ints.map { it*factor }
        }
    }
    fun sum(list: List<MutableList<Double>>,list1:List<MutableList<Double>>,n1: Int,n2: Int) {
        for (i in 0 until n1) {
            for (j in 0 until n2) {
                print((list[i][j] + list1[i][j]).toString() + " ")
            }
            println()
        }
    }
    fun multiplyTwoM(a: List<MutableList<Double>>,b:List<MutableList<Double>>,r1: Int,c1: Int,r2:Int,c2:Int) {
        for (i in 0 until r1) {
            for (j in 0 until c2) {
                var sum = 0.0
                for (k in 0 until c1) {
                    val ad = a[i][k]
                    val sd = b[k][j]
                    sum += ad*sd
                }
                print("${("%.3f".format(sum))} ")
            }
          println()
        }
    }
    private fun d2x(matrix: List<MutableList<Double>>): Double {
        return (matrix[0][0]*matrix[1][1]) - (matrix[0][1]*matrix[1][0])
    }
    private fun cof(row:Int,column: Int) = ((-1.0).pow(row+column)).roundToInt()
    fun determinantValue(matrix: List<MutableList<Double>>, row:Int, column:Int):Double{
          var res = 0.0
           return if(row < 3) {
               d2x(matrix)
           }
           else  {
               val list = mutableListOf(mutableListOf<Double>())
               for (h in 0 until column) {
                   for (i in 0 until row) {
                       list.add(mutableListOf())
                   }
                   for (k in 0 until row) {
                       for (j in 0 until row) {
                           if (k!=0 && h!=j) {
                               list[k].add(matrix[k][j]) //minor
                           }
                       }
                   }
                   val resL = mutableListOf(mutableListOf<Double>())
                   for (ele in list) {
                       if (ele.isNotEmpty()) resL.add(ele)
                   }
                   val cofactor = cof(0,h)
                   val minor = determinantValue(resL.drop(1),row-1,column-1)
                   res += (cofactor * (matrix[0][h] * minor))
                   list.clear()
                   resL.clear()
               }
               res
           }
    }
    private fun adjoin(matrix: List<MutableList<Double>>,row: Int,column: Int):List<MutableList<Double>> {
        val resMatrix = mutableListOf(mutableListOf<Double>())
        val minors = mutableListOf(listOf(mutableListOf<Double>()))
        val list = mutableListOf(mutableListOf<Double>())
         for (i in 0 until row) {
             for (j in 0 until column) {
                 for (p in 0 until row) {
                     list.add(mutableListOf())
                 }
                 for(k in 0 until row) {
                     for (h in 0 until column) {
                         if (i!=k && j!=h) {
                             list[k].add(matrix[k][h])
                         }
                     }
                 }
                 val resL = mutableListOf(mutableListOf<Double>())
                 for (ele in list) {
                     if (ele.isNotEmpty()) resL.add(ele)
                 }
                 //resMatrix[i][j] = cof(i,j) * determinantValue(resL.drop(1),row-1, column-1)
                 minors.add(resL.drop(1))
                 list.clear()
                 resL.clear()
             }
         }
        minors.removeAt(0)
        var cnt=0
        for (g in 0 until row) resMatrix.add(mutableListOf())
        for (q in 0 until row) {
            for (w in 0 until column) {
               resMatrix[q].add(cof(q,w) * determinantValue(minors[cnt++],row-1,column-1))
            }
        }
        resMatrix.removeAt(resMatrix.size-1)
        println(resMatrix)
        return resMatrix
    }
    fun calculateInverse(matrix: List<MutableList<Double>>, row: Int,column: Int) = adjoin(matrix,row, column) mulMatrix (1/ determinantValue(matrix,row,column))
    fun askChoice() = print("Your choice: ")
    fun resMessage() = println("The result is:")
    fun askForSize(m:String):List<Int> {
        print("Enter size of ${m}matrix: ")
        return readln().split(" ").map { it.toInt() }
    }
    fun eMessage() = println("The operation cannot be performed.")
}
fun List<List<Double>>.displayTranspose(r:Int,c:Int) {
    for (i in 0 until r) {
        for (j in 0 until c) {
            if (this[j][i] == -0.0) print("${abs(this[j][i])} ")
            else print("${this[j][i]} ")
        }
        println()
    }
}
class Transposition(private val r:Int,
                    private val c:Int,
                    private val matrix:List<MutableList<Double>>):NMP {
    fun mainD() {
        for (i in 0 until r) {
            for (j in 0 until c) {
                print("${matrix[j][i]} ")
            }
            println()
        }
    }
    fun sideD() {
        for (i in 0 until r) {
            for (j in 0 until c) {
                print("${matrix[abs(j-(r-1))][abs(i-(c-1))]} ")
            }
            println()
        }
    }
    fun vD() {
        for (i in 0 until r) {
            for (j in 0 until c) {
                print("${matrix[i][abs(j-(c-1))]} ")
            }
            println()
        }
    }
    fun hD() {
        for (i in 0 until r) {
            for (j in 0 until c) {
                print("${matrix[abs(i-(r-1))][j]} ")
            }
            println()
        }
    }
}
class MCalculator : NMP {
    private fun addMatrices() {
        val (n1,n2) = askForSize("first ")
        val m1 = matrix(n1,n2)
        val (n3,n4) = askForSize("second ")
        val m2 = matrix(n3,n4)
        val valid = n1 == n3 && n2 == n4 //for sum
        if (valid) {
            resMessage()
            sum(m1,m2,n1,n2)
        } else println("The operation cannot be performed.")

    }
    private fun mulByConst() {
        val (row,column) = askForSize("")
        val matrix = matrix(row,column)
        print("Enter constant: ")
        val const = readln().toDouble()
        resMessage()
        (matrix mulMatrix const).forEach { println(it.joinToString(separator = " ")) }
    }
    private fun multiplyMatrix() {
        val (n1,n2) = askForSize("first ")
        val m1 = matrix(n1,n2)
        val (n3,n4) = askForSize("second ")
        val m2 = matrix(n3,n4)
        val valid = n2 == n3 //for multiplication of two matrices
        if (valid) {
            resMessage()
            multiplyTwoM(m1,m2,n1,n2, n3, n4)
        } else eMessage()
    }
    private fun transpose() {
        listOf(
            "1. Main diagonal",
            "2. Side diagonal",
            "3. Vertical line",
            "4. Horizontal line"
        ).forEach { println(it) }
        askChoice()
        val choice = readln().toInt()
        val (row,column) = askForSize("")
        val matrix = matrix(row,column)
        val t = Transposition(row,column,matrix)
        resMessage()
        when(choice) {
            1 -> t.mainD()
            2 -> t.sideD()
            3 -> t.vD()
            4 -> t.hD()
        }
    }
    private fun determinant() {
        val (row,column) = askForSize("")
        val matrix = matrix(row,column)
        val valid = row == column
        if (valid) {
            resMessage()
            println(determinantValue(matrix, row, column))
        } else eMessage()
    }
    private fun inverse() {
        val (row,column) = askForSize("")
        println("Enter matrix:")
        val matrix = matrix(row,column)
        val valid = determinantValue(matrix,row,column) != 0.0 || row == column
        if (valid) {
            resMessage()
            calculateInverse(matrix,row,column).displayTranspose(row,column)
        } else println("This matrix doesn't have an inverse.")
    }
    fun start() {
       do {
           listOf(
               "1. Add matrices",
               "2. Multiply matrix by a constant",
               "3. Multiply matrices",
               "4. Transpose matrix",
               "5. Calculate a determinant",
               "6. Inverse matrix",
               "0. Exit "
           ).forEach { println(it) }
           askChoice()

           val choice = readln().toInt()

           when (choice) {
               1 -> addMatrices()
               2 -> mulByConst()
               3 -> multiplyMatrix()
               4 -> transpose()
               5 -> determinant()
               6 -> inverse()
           }
       } while (choice!=0)
    }
}
fun main() = MCalculator().start()
