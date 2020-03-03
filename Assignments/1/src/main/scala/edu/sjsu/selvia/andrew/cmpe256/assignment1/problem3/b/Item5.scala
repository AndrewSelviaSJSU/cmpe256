package edu.sjsu.selvia.andrew.cmpe256.assignment1.problem3.b

import edu.sjsu.selvia.andrew.cmpe256.assignment1.problem3.Utilities.samplesGroupedByType1

object Item5 extends App {
  print(
    samplesGroupedByType1
      .map(tuple => {
        val samples = tuple._2
        (tuple._1, samples.map(_ ("Defense").toInt).sum / samples.size)
      })
      .toSeq
      .minBy(_._2)
      ._1)
}
