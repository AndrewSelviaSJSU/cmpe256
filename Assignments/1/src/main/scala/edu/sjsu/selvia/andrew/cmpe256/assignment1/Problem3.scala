package edu.sjsu.selvia.andrew.cmpe256.assignment1

import com.github.tototoshi.csv.CSVReader

import scala.io.Source
import scala.math.{pow, sqrt}

object Problem3 extends App {
  private val all =
    CSVReader
      .open(Source fromResource "Pokemon.csv")
      .allWithHeaders
  val sampleSize = all.size // 800
  println(sampleSize)
  all foreach println




  // TODO: I think she means non-numeric properties (i.e. strings)


  val samplesWithNaN = all count(_("Type 2") == "")
  println(samplesWithNaN)
  val samplesGroupedByType1 = all.groupBy(_("Type 1"))
  val samplesGroupedByType1SortedBySize = samplesGroupedByType1.toSeq.sortBy(_._2.size)
  val min = samplesGroupedByType1SortedBySize.head._1
  println(min)
  val max = samplesGroupedByType1SortedBySize.last._1
  println(max)
  // 4. The group with highest average attack in “Type 1”
  private val fouur: Seq[(String, Int)] = samplesGroupedByType1
    .map(x => {
      val foo = x._2
      (x._1, foo.map(_("Attack").toInt).sum / foo.size)
    })
    .toSeq
    .sortBy(-_._2)
  println(fouur.head._1) // Dragon

  // 5. The group with lowest average defence in “Type 1”
  private val five = samplesGroupedByType1
    .map(x => {
      val foo = x._2
      (x._1, foo.map(_ ("Defense").toInt).sum / foo.size)
    })
    .toSeq
    .sortBy(_._2)
  println(five.head._1) // Normal

  // (c) (10pt) Drop the samples with NaN value and drop two columns [“#”,“Total”] as well. Generate a correlation map. Find out the most correlated and most un-correlated pairs.
  class Foo[T](x: Seq[T])(implicit num: Numeric[T]) {
    private def mean(x: Seq[T]) = num.toDouble(x.sum) / x.size
    private def standardDeviation(x: Seq[T]) = {
      val xMean = mean(x)
      sqrt(x.map(xi => pow(num.toDouble(xi) - xMean, 2)).sum / (x.size - 1))
    }
    val mean: Double = mean(x)
    val standardDeviation: Double = standardDeviation(x)
    def covary(y: Seq[T]): Double = {
      val xMean = mean
      val yMean = mean(y)
      x.zip(y).map { case (xi, yi) => (num.toDouble(xi) - xMean) * (num.toDouble(yi) - yMean) }.sum / (x.size - 1)
    }
    def correlate(y: Seq[T]): Double = covary(y) / standardDeviation / standardDeviation(y)
  }
  implicit def iterebleWithAvg[T](data: Seq[T])(implicit num: Numeric[T]): Foo[T] = new Foo(data)

  // test case
  val x = Vector(1, 2, 4, 3, 0, 0, 0)
  val y = Vector(1, 2, 3, 4, 0, 0, 0)
  println(x correlate y)

  val whitelist = Vector("Attack", "Defense", "HP", "Sp. Atk", "Sp. Def", "Speed")
  val stats =
    all
      .map(_.filter(whitelist contains _._1))
      .map(map => whitelist.map(map(_) toInt))
      .transpose
  val indices = stats.indices
  val tuples =
    indices
      .flatMap(i => indices.map(j => Set(i, j)))
      .toSet
      .filter(_.size == 2)
      .map(_.toList.sorted)
      .toList
      .sortBy(l => (l.head, l(1)))
      .map(l => (l.head, l(1), stats(l.head) correlate stats(l(1))))
  tuples
    .map { case (stat1Index, stat2Index, correlation) => s"The correlation between ${whitelist(stat1Index)} and ${whitelist(stat2Index)} is $correlation" }
    .foreach(println)
  val tuplesSortedByCorrelation = tuples.sortBy(_._3)
  val mostSimilar = tuplesSortedByCorrelation.last
  println(s"${whitelist(mostSimilar._1)} & ${whitelist(mostSimilar._2)} are most similar.")
  val leastSimilar = tuplesSortedByCorrelation.head
  println(s"${whitelist(leastSimilar._1)} & ${whitelist(leastSimilar._2)} are least similar.")
}
