package edu.sjsu.selvia.andrew.cmpe256.assignment1

object Problem1 extends App {
  // a
  val yearsExperience = Array(1.1, 1.3, 2.1, 2.2, 3.2)
  def median(seq: Seq[Double]) = {
    val length = seq.length
    val medianIndex = length / 2
    val sorted = seq.sorted
    if (length % 2 == 0) (sorted(medianIndex) + sorted(medianIndex + 1)) / 2 else sorted(medianIndex)
  }
  println(median(yearsExperience))

  // b
  val salaries = Array(39343, 46205, 37731, 43525, 54445)
  def mean(seq: Seq[Int]) = seq.sum / seq.length
  println(mean(salaries))

  // c
  def minMax(seq: Seq[Double]) = seq.map(x => (x - seq.min) / (seq.max - seq.min))
  val yearsExperience2 = Array(1.1, 1.3, 2.1, 2.1, 2.2, 3.2)
  println(minMax(yearsExperience2))
  val salaries2 = Array(39343, 46205, 37731, 43525, 44249, 54445).map(_.toDouble)
  println(minMax(salaries2))
}