package edu.sjsu.selvia.andrew.cmpe256.assignment1

import scala.math.{pow, round}

object Utilities {
  def roundAt(p: Int)(n: Double): Double = {
    val s = pow(10, p)
    round(n * s) / s
  }
  val roundAt3: Double => Double = roundAt(3)
}
