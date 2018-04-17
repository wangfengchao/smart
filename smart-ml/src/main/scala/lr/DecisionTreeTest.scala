package lr

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.evaluation.{BinaryClassificationMetrics, MulticlassMetrics}
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.tree.model.DecisionTreeModel
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by fc.w on 2018/3/6.
  */
object DecisionTreeTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[1]").setAppName("decision_tree_launcher")
    val sc = new SparkContext(conf)

    Logger.getRootLogger.setLevel(Level.WARN)

    val path = System.getProperty("user.dir")
    val dataRDD = MLUtils.loadLibSVMFile(sc, path + "/smart-ml/src/main/scala/lr/data.txt")

    // 样本数据划分,训练样本占0.8, 测试样本占0.2
    val dataParts = dataRDD.randomSplit(Array(0.8, 0.2))
    val trainRDD = dataParts(0)
    val testRDD = dataParts(1)

    // 决策树参数
    val numClasses = 2
    val categoricalFeaturesInfo = Map[Int, Int]()
    val impurity = "gini"
    val maxDepth = 3
    val maxBins = 32
    // 建立决策树模型并训练
    val model = DecisionTree.trainClassifier(trainRDD, numClasses, categoricalFeaturesInfo, impurity, maxDepth, maxBins)

    /*决策树的精确度*/
    val predectionAndLabeledDT=testRDD.map { point =>
      val predectLabeled = if (model.predict(point.features) > 0.5) 1.0 else 0.0
      (predectLabeled,point.label)
    }
    val metricsDT = new MulticlassMetrics(predectionAndLabeledDT)
    println("Precision = " + metricsDT.precision)

    /*决策树的PR曲线和AOC曲线*/
    val dtMetrics = Seq(model).map{ model =>
      val scoreAndLabels = testRDD.map { point =>
        val score = model.predict(point.features)
        (if (score > 0.5) 1.0 else 0.0, point.label)
      }
      val metrics = new BinaryClassificationMetrics(scoreAndLabels)
      (model.getClass.getSimpleName, metrics.areaUnderPR, metrics.areaUnderROC)
    }

    val allMetrics = dtMetrics
    allMetrics.foreach{ case (m, pr, roc) =>
      println(f"$m, Area under PR: ${pr * 100.0}%2.4f%%, Area under ROC: ${roc * 100.0}%2.4f%%")
    }

    // 对测试样本进行测试
    val predictionAndLabel = testRDD.map { point =>
      val score = model.predict(point.features)
      (score, point.label, point.features)
    }

    val showPredict = predictionAndLabel.take(50)
    println("Prediction" + "\t" + "Label" + "\t" + "Data")
    for (i <- 0 to showPredict.length - 1) {
      println(showPredict(i)._1 + "\t" + showPredict(i)._2 + "\t" + showPredict(i)._3)
    }

    // 误差计算
    val accuracy = 1.0 * predictionAndLabel.filter(x => x._1 == x._2).count() / testRDD.count()
    println("Accuracy = " + accuracy)

    // 保存模型
//    val ModelPath = "hdfs://master:9000/ml/model/Decision_Tree_Model"
//    model.save(sc, ModelPath)
//    val sameModel = DecisionTreeModel.load(sc, ModelPath)

  }

}
