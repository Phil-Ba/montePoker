package at.bayava.montepoker

import at.bayava.montepoker.io.ClReader

import scala.io.StdIn

/**
	* Created by philba on 5/22/17.
	*/
object Main {

	def main(args: Array[String]): Unit = {
		var ok = true
		val clReader = new ClReader()
		while (ok) {
			val line = StdIn.readLine()
			clReader.parse(line)
		}
	}

}
