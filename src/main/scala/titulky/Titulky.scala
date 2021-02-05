package titulky

import io.Source

object Titulky {
  private val line = raw"(.*) --> (.*)".r

  private def decode(t: String): Double = 1.0
  private def encode(t: Double): String = "Hello"

  private def processLine(l: String, stretch: Double): String = {
    l match {
      case line(start, stop) => {
        val startStretched = encode(decode(start) * stretch)
        val endStretched = encode(decode(start) * stretch)
        s"$startStretched --> $endStretched"
      }
      case _ => l
    }
  }

  def main(args: Array[String]) {
    Source.stdin
      .getLines()
      .map(processLine(_, 1.0))
      .foreach(println)
  }
}
