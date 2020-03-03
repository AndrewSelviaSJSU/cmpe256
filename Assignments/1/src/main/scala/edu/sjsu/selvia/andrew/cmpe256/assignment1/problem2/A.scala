package edu.sjsu.selvia.andrew.cmpe256.assignment1.problem2

import edu.sjsu.selvia.andrew.cmpe256.assignment1.Utilities.roundAt3
import edu.sjsu.selvia.andrew.cmpe256.assignment1.problem2.Utilities._

object A extends App {
  def cosineSimilarity(x: Seq[Int], y: Seq[Int]) = roundAt3(dotProduct(x, y) / (magnitude(x) * magnitude(y)))
  print(latexSimilarityTable(ratingsByUser, cosineSimilarity))
}
