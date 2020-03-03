package edu.sjsu.selvia.andrew.cmpe256.assignment1.problem1

object A extends App {
  val yearsExperience = Array(1.1, 1.3, 2.1, 2.2, 3.2)
  def median(seq: Seq[Double]) = {
    val length = seq.length
    val medianIndex = length / 2
    val sorted = seq.sorted
    if (length % 2 == 0) (sorted(medianIndex) + sorted(medianIndex + 1)) / 2 else sorted(medianIndex)
  }
  print(median(yearsExperience))
}
