package edu.sjsu.selvia.andrew.cmpe256.assignment1.problem2

import edu.sjsu.selvia.andrew.cmpe256.assignment1.Utilities.roundAt3
import edu.sjsu.selvia.andrew.cmpe256.assignment1.problem2.Utilities._

object B extends App {
  def jaccard(x: Seq[Int], y: Seq[Int]) = {
    val sums = x zip y map { case (i, j) => i + j }
    roundAt3(sums.count(_ == 2).toDouble / sums.count(_ > 0).toDouble)
  }
  print(latexSimilarityTable(ratingsByUser.map(_.map(rating => if (rating < 3) 0 else 1)), jaccard))
}
