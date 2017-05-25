package at.bayava.montepoker.model

/**
	* Created by philba on 5/22/17.
	*/
class Hand(val cards: Seq[Card]) {
	def +(card: Card*): Hand = new Hand(cards ++ card)

	def -(card: Card*): Hand = new Hand(cards diff card)

	def --(card: Hand.Pair*): Hand = new Hand(cards diff card)

	def highCard: Card = cards.max

	def pairs: Iterable[Hand.Pair] = {
		cards.groupBy(_.value)
			.values
			.withFilter(_.length == 2)
			.map(pair => (pair.head, pair.last))
	}

	override def toString: String = "Hand(" + cards.mkString(", ") + ")"
}

object Hand {
	private val cardRegex = "\\w{1,2}[CDHS]".r
	type Pair = (Card, Card)

	def apply(s: String): Hand = {
		val cards = cardRegex.findAllIn(s)
			.map(m => Card(m))
		new Hand(cards.toSeq)
	}

	implicit object PairOrdering extends Ordering[Hand.Pair] {
		override def compare(x: Hand.Pair, y: Hand.Pair): Int = x._1 compareTo y._1
	}

}

