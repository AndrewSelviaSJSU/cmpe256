package edu.sjsu.selvia.andrew.cmpe256.assignment1.problem3.b

import edu.sjsu.selvia.andrew.cmpe256.assignment1.problem3.Utilities.all

object Item3 extends App {
  val samplesGroupedByType1 = all.groupBy(_("Type 1"))
  val samplesGroupedByType1SortedBySize = samplesGroupedByType1.toSeq.sortBy(_._2.size)
  println(s"Largest: ${samplesGroupedByType1SortedBySize.last._1}")
  print(s"Smallest: ${samplesGroupedByType1SortedBySize.head._1}")
}
