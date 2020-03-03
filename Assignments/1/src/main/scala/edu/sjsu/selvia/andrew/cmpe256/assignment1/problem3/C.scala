package edu.sjsu.selvia.andrew.cmpe256.assignment1.problem3

import edu.sjsu.selvia.andrew.cmpe256.assignment1.Utilities.roundAt3
import edu.sjsu.selvia.andrew.cmpe256.assignment1.problem2.Utilities.similarityTable
import edu.sjsu.selvia.andrew.cmpe256.assignment1.problem3.Utilities.all

import scala.math.{pow, sqrt}

object C extends App {
  def _mean[T](x: Seq[T])(implicit num: Numeric[T]) = num.toDouble(x.sum) / x.size
  def _covariance[T](x: Seq[T], y: Seq[T])(implicit num: Numeric[T]): Double = {
    val xMean = x.mean
    val yMean = y.mean
    x.zip(y).map { case (xi, yi) => (num.toDouble(xi) - xMean) * (num.toDouble(yi) - yMean) }.sum / (x.size - 1)
  }
  def _standardDeviation[T](x: Seq[T])(implicit num: Numeric[T]) = {
    val xMean = x.mean
    sqrt(x.map(xi => pow(num.toDouble(xi) - xMean, 2)).sum / (x.size - 1))
  }
  def _correlation[T](x: Seq[T], y: Seq[T])(implicit num: Numeric[T]): Double = roundAt3((x covariance y) / x.standardDeviation / y.standardDeviation)
  class Foo[T](x: Seq[T])(implicit num: Numeric[T]) {
    def mean: Double = _mean(x)
    def standardDeviation: Double = _standardDeviation(x)
    def covariance(y: Seq[T]): Double = _covariance(x, y)
    def correlation(y: Seq[T]): Double = _correlation(x, y)
  }
  implicit def iterebleWithAvg[T](data: Seq[T])(implicit num: Numeric[T]): Foo[T] = new Foo(data)

  val whitelist = Vector("Attack", "Defense", "HP", "Sp. Atk", "Sp. Def", "Speed")
  val stats =
    all
      .map(_.filter(whitelist contains _._1))
      .map(map => whitelist.map(map(_) toInt))
      .transpose
  def latexSimilarityTable(vectors: Seq[Seq[Int]], similarityCalculation: (Seq[Int], Seq[Int]) => Double): String = similarityTable(vectors, similarityCalculation).zipWithIndex.flatMap { case (row, rowIndex) => List("\\hline", s"${(whitelist(rowIndex) :: row.map(_.toString).toList) mkString "&"}\\\\") map("         " + _) }  mkString "\n"
  print(latexSimilarityTable(stats, _correlation))
}
