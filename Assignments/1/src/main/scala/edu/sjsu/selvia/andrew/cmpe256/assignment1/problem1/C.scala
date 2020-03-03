package edu.sjsu.selvia.andrew.cmpe256.assignment1.problem1

import edu.sjsu.selvia.andrew.cmpe256.assignment1.Utilities.roundAt3

object C extends App {
  def minMax(seq: Seq[Double]) = seq map(x => (x - seq.min) / (seq.max - seq.min)) map roundAt3
  print(
    minMax(Array(1.1, 1.3, 2.1, 2.1, 2.2, 3.2)),
    minMax(Array(39343, 46205, 37731, 43525, 44249, 54445).map(_.toDouble)))
}
