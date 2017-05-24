package at.bayava.montepoker.model

/**
	* Created by philba on 5/22/17.
	*/
class Hand(val cards: Seq[Card]) {

	def +(card: Card): Hand = new Hand(cards :+ card)

}
