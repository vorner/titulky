package titulky

import io.Source

object Titulky {
  private val line = raw"(.*) --> (.*)".r
  private val time = raw"(\d+):(\d+):(\d+),(\d+)".r

  private def decode(t: String): Double = {
    t match {
      case time(h, m, s, p) => h.toDouble * 3600.0 + m.toDouble * 60.0 + s.toDouble + (p.toDouble / 1000.0)
    }
  }
  private def encode(t: Double): String = {
    val hours = (t / 3600.0).toInt
    val noHours = t - (hours * 3600.0)
    val minutes = (noHours / 60.0).toInt
    val seconds = noHours - minutes * 60.0
    val wholeSeconds = seconds.toInt
    val parts = ((seconds - wholeSeconds) * 1000.0).toInt
    "%02d:%02d:%02d,%03d".format(hours, minutes, wholeSeconds, parts)
  }

  private def processLine(l: String, stretch: Double): String = {
    l match {
      case line(start, stop) => {
        val startStretched = encode(decode(start) * stretch)
        val endStretched = encode(decode(stop) * stretch)
        s"$startStretched --> $endStretched"
      }
      case _ => l
    }
  }

  def main(args: Array[String]) {
    val orig = decode(args(0))
    val target = decode(args(1))
    val mod = target / orig
    Source.stdin
      .getLines()
      .map(processLine(_, mod))
      .foreach(println)
  }
}
