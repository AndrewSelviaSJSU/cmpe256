package edu.sjsu.selvia.andrew.cmpe256.assignment1.problem4

import org.apache.spark.SparkContext
import org.apache.spark.sql.SaveMode.Overwrite
import org.apache.spark.sql.SparkSession

object Problem4 {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext( "local", "Ratings")
    // prefix for inputPath & outputPath when running locally: cmpe256/Assignments/1/
    val inputPath = "src/main/resources/ratings-test.csv"
    val outputPath = "out/problem4"

    def usingDatasetApi(): Unit =
      SparkSession
        .builder
        .getOrCreate
        .read
        .option("inferSchema", value = true)
        .option("header", value = true)
        .csv(inputPath)
        .groupBy("movieId")
        .avg("rating")
        .sort("movieId")
        .coalesce(1)
        .write
        .mode(Overwrite)
        .options(Map(("header", "true")))
        .csv(outputPath)

    def usingRddApi(): Unit = {
      // TODO: this is carried over from Problem3; DRY
      class Foo[T](x: Iterable[T])(implicit num: Numeric[T]) {
        def mean: Double = num.toDouble(x.sum) / x.size
      }
      implicit def iterebleWithAvg[T](data: Iterable[T])(implicit num: Numeric[T]): Foo[T] = new Foo(data)
      sc
        .textFile(inputPath)
        .mapPartitionsWithIndex((index, lines) => if (index == 0) lines drop 1 else lines)
        .map(line => {
          val splitLine = line split ","
          (splitLine(1).toInt, splitLine(2).toDouble)
        })
        .groupByKey
        .sortByKey()
        .mapValues(_ mean)
        .map(_.productIterator.mkString(","))
        .saveAsTextFile(outputPath)
    }

    usingRddApi
  }
}
