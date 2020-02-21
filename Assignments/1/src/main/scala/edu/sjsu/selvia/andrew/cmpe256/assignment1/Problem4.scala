package edu.sjsu.selvia.andrew.cmpe256.assignment1

import org.apache.spark.SparkContext
import org.apache.spark.sql.SaveMode.Overwrite
import org.apache.spark.sql.SparkSession

object Problem4 {
  def main(args: Array[String]): Unit = {
    // In this problem, you will write a simple Spark code to generate the average rating of each item in the dataset: https://www.kaggle.com/deebakaz/ratings.
    // Please submit both the output (a .csv file) and your code.
    // The output should have one item (ItemID, Avg Rating) per line.
    // You can run your code locally or on HPC.

    val sc = new SparkContext( "local", "Ratings")
    val path = "src/main/resources/ratings-test.csv"

    def usingDatasetApi(): Unit =
      SparkSession
        .builder
        .getOrCreate
        .read
        .option("inferSchema", value = true)
        .option("header", value = true)
        .csv(path)
        .groupBy("movieId")
        .avg("rating")
        .sort("movieId")
        .coalesce(1)
        .write
        .mode(Overwrite)
        .options(Map(("header", "true")))
        .csv("output.csv")

    def usingRddApi(): Unit = {
      // TODO: this is carried over from Problem3; DRY
      class Foo[T](x: Iterable[T])(implicit num: Numeric[T]) {
        def mean: Double = num.toDouble(x.sum) / x.size
      }
      implicit def iterebleWithAvg[T](data: Iterable[T])(implicit num: Numeric[T]): Foo[T] = new Foo(data)
      sc
        .textFile(path)
        .mapPartitionsWithIndex((index, lines) => if (index == 0) lines drop 1 else lines)
        .map(line => {
          val splitLine = line split ","
          (splitLine(1).toInt, splitLine(2).toDouble)
        })
        .groupByKey
        .sortByKey()
        .mapValues(_ mean)
        .map(_.productIterator.mkString(","))
        .saveAsTextFile("output2.csv")
    }

    usingRddApi
  }
}
