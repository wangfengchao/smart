package spark_ml

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by fc.w on 2017/10/30.
  */
object MovieLens_Data_3 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("3_MovieLens_u.user").setMaster("local[2]")
    val sc = new SparkContext(conf)
    sc.setLogLevel("WARN")
    val path = System.getProperty("user.dir")
    val userData = sc.textFile(s"${path}/smart-ml/src/main/resources/data/spark_ml/ml-100k/u.user")

    val userFields = userData.map(line => line.split("\\|"))
    val numUsers = userFields.map(fields => fields(0)).count()
    val numGenders = userFields.map(fildes => fildes(2)).distinct().count()
    val numOccupations = userFields.map(fields => fields(3)).distinct().count()
    val numZipcodes = userFields.map(fields => fields(4)).distinct().count()
    val age = userFields.map(fields => fields(1)).collect()

//    print(s"Users: ${numUsers}, genders: ${numGenders}, occupations: ${numOccupations}, ZIP codes: ${numZipcodes}")

  }

}
