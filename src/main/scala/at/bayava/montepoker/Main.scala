package at.bayava.montepoker

import at.bayava.montepoker.io.ClReader

import scala.io.StdIn

/**
	* Created by philba on 5/22/17.
	*/
object Main {

	def main(args: Array[String]): Unit = {
		println("Usage")
		println("Input your hand, the card pool, the number of players and number of simulations in the following format")
		println("[Card]{2} [Card]{2} \\d \\d")
		println("Where [Cards] is [1-9JQKA][CDHS]")
		//		println("Where [Cards] is [1-9J(ack)Q(ueen)K(ing)A(ce)][C(lubs)D(iamonds)H(earts)S(pades)]")

		var ok = true
		val clReader = new ClReader()
		while (ok) {
			val line = StdIn.readLine()
			clReader.parse(line)
		}
	}

}
