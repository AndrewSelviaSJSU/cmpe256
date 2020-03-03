package edu.sjsu.selvia.andrew.cmpe256.assignment1.problem3

import com.github.tototoshi.csv.CSVReader

import scala.collection.immutable
import scala.io.Source

object Utilities {
  val all: immutable.Seq[Map[String, String]] =
    CSVReader
      .open(Source fromInputStream getClass.getResourceAsStream("/Pokemon.csv"))
      .allWithHeaders
  val samplesGroupedByType1: Map[String, immutable.Seq[Map[String, String]]] = all.groupBy(_("Type 1"))
}
