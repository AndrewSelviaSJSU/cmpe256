package edu.sjsu.selvia.andrew.cmpe256.assignment1.problem1

object B extends App {
  val salaries = Array(39343, 46205, 37731, 43525, 54445)
  def mean(seq: Seq[Int]) = seq.sum / seq.length
  print(mean(salaries))
}
