package edu.sjsu.selvia.andrew.cmpe256.assignment1.problem3.b

import edu.sjsu.selvia.andrew.cmpe256.assignment1.problem3.Utilities.samplesGroupedByType1

object Item4 extends App {
  print(
    samplesGroupedByType1
      .map(tuple => {
        val samples = tuple._2
        (tuple._1, samples.map(_ ("Attack").toInt).sum / samples.size)
      })
      .toSeq
      .minBy(-_._2)
      ._1)
}
