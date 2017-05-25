package at.bayava.montepoker.model

/**
	* Created by philba on 5/22/17.
	*/
class Hand(val cards: Seq[Card]) {

	def +(card: Card*): Hand = new Hand(cards ++ card)

	def -(card: Card*): Hand = new Hand(cards diff card)

	def -(card: Hand.Pair*): Hand = new Hand(cards diff card)

	def highCard: Card = cards.max

	def pairs: Iterable[Hand.Pair] = {
		cards.groupBy(_.value)
			.values
			.withFilter(_.length == 2)
			.map(pair => (pair.head, pair.last))
	}

}

object Hand {
	type Pair = (Card, Card)

	implicit object PairOrdering extends Ordering[Hand.Pair] {
		override def compare(x: Hand.Pair, y: Hand.Pair): Int = x._1 compareTo y._1
	}

}

