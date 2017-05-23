package at.bayava.montepoker.model

/**
	* Created by philba on 5/22/17.
	*/
case class Card(value: CardValues.Value, suite: Suites.Value) {

}

object Card {

	def apply(s: String): Card = {
		val value = s.init.toString
		val suite = s.last.toString
		new Card(CardValues.withName(value), Suites.withName(suite))
	}

}