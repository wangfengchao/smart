package com.smart.breeze

import breeze.linalg.{DenseMatrix, _}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Breeze函数库
  * Created by fc.w on 2017/6/16.
  */
object BreezeTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("breeze_test").setMaster("local[1]")
    val sc = new SparkContext(conf)
    Logger.getRootLogger.setLevel(Level.WARN)

    // 3.1.1 Breeze 创建函数
    val m1 = DenseMatrix.zeros[Double](2, 3)
    println(s"DenseMatrix.zeros[Double](2, 3)初始化一个2行3列的矩阵：\n ${m1}")

    val v1 = DenseVector.zeros[Double](3)
    println(s"\nDenseVector.zeros[Double](3) 初始值为0，长度为3的向量：\n ${v1}")

    val v2 = DenseVector.ones[Double](3)
    println(s"\nDenseVector.ones[Double](3) 初始为1，长度为3的向量：\n${v2}")

    val v3 = DenseVector.fill[Double](3){5.0}
    println(s"\nDenseVector.fill[Double](3){5.0}:\n ${v3}")

    val v4 = DenseVector.range(1, 10, 2)
    println(s"\nDenseVector.range(1, 10, 2):\n ${v4}")

    val m2 = DenseMatrix.eye[Double](3)
    println(s"\nDenseMatrix.eye[Double](3):\n${m2}")

    val v6 = diag(DenseVector(1.0, 2.0, 3.0))
    println(s"\ndiag(DenseVector(1.0, 2.0, 3.0)):\n${v6}")

    val m3 = DenseMatrix((1.0, 2.0), (3.0, 4.0))
    println(s"\nDenseMatrix((1.0, 2.0), (3.0, 4.0)):\n${m3}")

    val v8 = DenseVector(1, 2, 3, 4)
    println(s"\nDenseVector(1, 2, 3, 4):\n${v8}")

    val v9 = DenseVector(1, 2, 3, 4).t
    println(s"\nDenseVector(1, 2, 3, 4).t:\n${v9}")

    val v10 = DenseVector.tabulate(3){i => 2 * i}
    println(s"\nDenseVector.tabulate(3){i => 2 * i}:\n${v10}")

    val m4 = DenseMatrix.tabulate(3, 2){case(i, j) => i + j}
    println(s"\nDenseMatrix.tabulate(3, 2){case(i, j) => i + j}:\n${m4}")

    val v11 = new DenseVector(Array(1, 2, 3, 4))
    println(s"\nDenseVector(Array(1, 2, 3, 4)):\n${v11}")

    val v12 = new DenseMatrix(2, 3, Array(1, 2, 3, 4, 5, 6))
    println(s"\nDenseMatrix(2, 3, Array(1, 2, 3, 4, 5, 6)):\n${v12}")

    val v13 = DenseVector.rand(4)
    println(s"\nv13:\n${v13}")

    val v14 = DenseMatrix.rand(2, 3)
    println(s"\nv14:\n${v14}")


    // 3.1.2 Breeze 元素访问
    val a = DenseVector(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(s"\na(0):\n${a(0)}")
    println(s"\na(1 to 4):\n${a(1 to 4)}")
    println(s"\na(5 to 0 by -1):\n${a(5 to 0 by -1)}")
    println(s"\na(1 to -1):\n${a(1 to -1)}")
    println(s"\na(-1):\n${a(-1)}")

    val m= DenseMatrix((1.0, 2.0, 3.0), (3.0, 4.0, 5.0))
    println(s"\nm(0, 1):   ${m(0, 1)}")
    println(s"\nm(::, 1):  ${m(::, 1)}")

    // 3.1.2 Breeze 元素操作
    val m_1 = DenseMatrix((1.0, 2.0, 3.0), (3.0, 4.0, 5.0))
    println("\n\n"+m_1)
    println(s"\nm_1.reshape(3, 2):\n${m_1.reshape(3, 2)}")
    println(s"\nm_1.toDenseVector:\n${m_1.toDenseVector}")

    val m_2 = DenseMatrix((1.0, 2.0, 3.0), (3.0, 4.0, 5.0), (7.0, 8.0, 9.0))
    println(s"\nlowerTriangular(m_2):\n${lowerTriangular(m_2)}")
    println(s"\nupperTriangular(m_2):\n${upperTriangular(m_2)}")
    println(s"\nm_2.copy:\n${m_2.copy}")
    println(s"\ndiag(m_2):\n${diag(m_2)}")

    println(s"\nm_2(::, 2) := 5.0: \n${m_2(::, 2) := 5.0}")
    println(m_2)

    println(s"\nm_2(1 to 2, 1 to 2) := 5.0: \n${m_2(1 to 2, 1 to 2) := 5.0}")
    println(m_2)


    val a_1 = DenseVector(1, 2, 3, 4, 5, 6, 7, 8, 9)
    println(s"\n${a_1(1 to 4) := 5}")
    println(s"\n${a_1}")
    println(s"\n${a_1(1 to 4) := DenseVector(1, 3, 6, 7)}")
    println(s"\n${a_1}")


    val a1 = DenseMatrix((1.0, 2.0, 3.0), (3.0, 4.0, 5.0))
    val a2 = DenseMatrix((1.0, 1.0, 1.0), (2.0, 2.0, 2.0))
    println(s"\nDenseMatrix.vertcat(a1, a2):\n${DenseMatrix.vertcat(a1, a2)}")
    println(s"\nDenseMatrix.horzcat(a1, a2):\n${DenseMatrix.horzcat(a1, a2)}")

    val d1 = DenseVector(1, 2, 3, 4)
    val d2 = DenseVector(1, 1, 1, 1)
    println(s"\nDenseVector.vertcat(d1, d2):\n${DenseVector.vertcat(d1, d2)}")
    println(s"\nDenseVector.horzcat(d1, d2):\n${DenseVector.horzcat(d1, d2)}")



    // 3.1.3 数值计算函数
    val a_3 = DenseMatrix((1.0, 2.0, 3.0), (3.0, 4.0, 5.0))
    val b_3 = DenseMatrix((1.0, 1.0, 1.0), (2.0, 2.0, 2.0))
    println(s"\na_3 + b_3: \n${a_3 + b_3}")
    println(s"\na_3 :* b_3: \n${a_3 :* b_3}")
    println(s"\na_3 :/ b_3: \n${a_3 :/ b_3}")
    println(s"\na_3 :< b_3: \n${a_3 :< b_3}")
    println(s"\na_3 :== b_3: \n${a_3 :== b_3}")
    println(s"\na_3 :+= b_3: \n${a_3 :+= b_3}")
    println(s"\na_3 :*= b_3: \n${a_3 :*= b_3}")
    println(s"\nmax(a_3): \n${max(a_3)}")
    println(s"\nargmax(a_3): \n${argmax(a_3)}")
    println(s"\nDenseVector(1, 2, 3, 4) dot DenseVector(1, 1, 1, 1): \n${DenseVector(1, 2, 3, 4) dot DenseVector(1, 1, 1, 1)}")

    // 3.1.4 Breeze 求和函数
    val m_4 = DenseMatrix((1.0, 2.0, 3.0), (3.0, 4.0, 5.0), (7.0, 8.0, 9.0))
    println(s"\nsum(m_4):\n${sum(m_4)}")
    println(s"\nsum(m_4, Axis._0):\n${sum(m_4, Axis._0)}")
    println(s"\nsum(m_4, Axis._1):\n${sum(m_4, Axis._1)}")
    println(s"\ntrace(m_4):\n${trace(m_4)}")
    println(s"\naccumulate(DenseVector(1, 2, 3, 4)):\n${accumulate(DenseVector(1, 2, 3, 4))}")


    // 3.1.6 Breeze 线性代数函数
    val m_5 = DenseMatrix((1.0, 2.0, 3.0), (3.0, 4.0, 5.0), (7.0, 8.0, 9.0))
    val b_5 = DenseMatrix((1.0, 1.0, 1.0), (1.0, 1.0, 1.0), (1.0, 1.0, 1.0))
    println(s"\nm_5 除以 b_5:\n${m_5 / b_5}")
    println(s"\nm_5.t: \n${m_5.t}")
    println(s"\ndet(m_5): \n${det(m_5)}")
//    println(s"\ninv(m_5): \n${inv(m_5)}")


    println(s"\n${val svd.SVD(u, s, v) = svd(b_5)}")

  }

}
