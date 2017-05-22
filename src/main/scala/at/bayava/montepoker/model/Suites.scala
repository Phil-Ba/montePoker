package at.bayava.montepoker.model

/**
	* Created by philba on 5/22/17.
	*/
object Suites {

	sealed trait Suite {
		override def toString: String = getClass.getSimpleName
	}

	object Clubs extends Suite

	object Diamonds extends Suite

	object Hearts extends Suite

	object Spades extends Suite

	def apply(s: String): Suite = s match {
		case "C" => Clubs
		case "D" => Diamonds
		case "H" => Hearts
		case "S" => Spades
	}
}
