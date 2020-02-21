package edu.sjsu.selvia.andrew.cmpe256.assignment1

import scala.math.{pow, round, sqrt}

object Problem2 extends App {
  def dotProduct(x: Seq[Int], y: Seq[Int]) = x zip y map { case (i, j) => i * j } sum
  def magnitude(x: Seq[Int]) = sqrt(dotProduct(x, x))
  def roundAt(p: Int)(n: Double): Double = {
    val s = pow(10, p)
    round(n * s) / s
  }
  val roundAt3 = roundAt(3) _
  def cosineSimilarity(x: Seq[Int], y: Seq[Int]) = roundAt3(dotProduct(x, y) / (magnitude(x) * magnitude(y)))
  def jaccard(x: Seq[Int], y: Seq[Int]) = {
    val sums = x zip y map { case (i, j) => i + j }
    roundAt3(sums.count(_ == 2).toDouble / sums.count(_ > 0).toDouble)
  }
  def printSimilarityInfo(vectors: Vector[Vector[Int]], similarityCalculation: (Seq[Int], Seq[Int]) => Double, prefix: String): Unit = {
    vectors foreach println
    val indices = vectors.indices
    val tuples = indices
      .flatMap(i => indices.map(j => Set(i, j)))
      .toSet
      .filter(_.size == 2)
      .map(_.toList.sorted)
      .toList
      .sortBy(l => (l.head, l(1)))
      .map(l => (l.head + 1, l(1) + 1, similarityCalculation(vectors(l.head), vectors(l(1)))))
    tuples
      .map(t => s"$prefix similarity for User ${t._1} and User ${t._2}: ${t._3}")
      .foreach(println)
    val mostSimilar = tuples.minBy(-_._3)
    println(s"Users ${mostSimilar._1} & ${mostSimilar._2} are most similar.")
  }
  val ratingsByUser = Vector(
    Vector(2, 4, 3, 1, 4),
    Vector(5, 1, 2, 4, 3),
    Vector(3, 2, 3, 5, 4),
    Vector(1, 5, 2, 2, 1),
    Vector(2, 1, 4, 2, 5))
  printSimilarityInfo(ratingsByUser, cosineSimilarity, "Cosine")
  printSimilarityInfo(ratingsByUser.map(_.map(rating => if (rating < 3) 0 else 1)), jaccard, "Jaccard")
}