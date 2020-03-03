package edu.sjsu.selvia.andrew.cmpe256.assignment1.problem2

import scala.collection.immutable
import scala.math.sqrt

object Utilities {
  def dotProduct(x: Seq[Int], y: Seq[Int]): Int = x zip y map { case (i, j) => i * j } sum
  def magnitude(x: Seq[Int]): Double = sqrt(dotProduct(x, x))
  val ratingsByUser = Vector(
    Vector(2, 4, 3, 1, 4),
    Vector(5, 1, 2, 4, 3),
    Vector(3, 2, 3, 5, 4),
    Vector(1, 5, 2, 2, 1),
    Vector(2, 1, 4, 2, 5))
  def similarityTable(vectors: Seq[Seq[Int]], similarityCalculation: (Seq[Int], Seq[Int]) => Double): immutable.Seq[immutable.IndexedSeq[Double]] = {
    val indices = vectors.indices
    val indexSetLists = indices.map(i => indices.map(j => Set(i, j)))
    import scala.collection.breakOut
    val similaritiesByUserIds: Map[Set[Int], Double] = indexSetLists
      .flatten[Set[Int]]
      .map((indexSet: Set[Int]) => (Set(indexSet.head, indexSet.last), similarityCalculation(vectors(indexSet.head), vectors(indexSet.last))))(breakOut)
    indexSetLists map(_ map similaritiesByUserIds)
  }
  def latexSimilarityTable(vectors: Seq[Seq[Int]], similarityCalculation: (Seq[Int], Seq[Int]) => Double): String = similarityTable(vectors, similarityCalculation).zipWithIndex.flatMap { case (row, rowIndex) => List("\\hline", s"${((rowIndex + 1).toString :: row.map(_.toString).toList) mkString "&"}\\\\") map("         " + _) }  mkString "\n"
}
