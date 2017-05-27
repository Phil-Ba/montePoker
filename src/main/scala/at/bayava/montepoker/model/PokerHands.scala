package at.bayava.montepoker.model

import at.bayava.montepoker.model.Hand.PairOrdering

/**
	*
	*/
object PokerHands {

	trait PokerHand[T <: PokerHand[T]] extends Ordered[T] {
		self: T =>
		val hand: Hand

		def compareByHighCard(that: Hand): Int = PokerHand.compareByHighCard(this.hand, that)
	}

	object PokerHand {
		protected[montepoker] def compareByHighCard(h1: Hand, h2: Hand): Int = {
			val h1Sorted = h1.cards.sorted
			val h2Sorted = h2.cards.sorted

			val compares = h1Sorted.zip(h2Sorted)
				.map((t: (Card, Card)) => () => t._1 compareTo t._2)

			firstNotEqual(compares: _*) match {
				case Some(i) => i
				case None => 0
			}
		}

		protected def firstNotEqual(compares: (() => Int)*): Option[Int] = {
			for (compare <- compares) {
				val result = compare()
				if (result != 0) {
					return Some(result)
				}
			}
			None
		}
	}

	class TwoPair(val hand: Hand, val pairs: Seq[Hand.Pair]) extends PokerHand[TwoPair] {
		override def compare(that: TwoPair): Int = {
			firstNotEqual(() => this.pairs.max compareTo that.pairs.max,
				() => this.pairs.min compareTo that.pairs.min) match {
				case Some(r) => r
				case None => PokerHand.compareByHighCard(this.hand -- (this.pairs: _*), that.hand -- (that.pairs: _*))
			}
		}

		protected def firstNotEqual(compares: (() => Int)*): Option[Int] = {
			for (compare <- compares) {
				val result = compare()
				if (result != 0) {
					return Some(result)
				}
			}
			None
		}
	}

	object TwoPair {
		def unapply(arg: Hand): Option[TwoPair] = {
			arg.pairs match {
				case a@(_ :: _ :: _) => Some(new TwoPair(arg, a.toSeq))
				case _ => None
			}
		}
	}


	class Pair(val hand: Hand, val pair: Hand.Pair) extends PokerHand[Pair] {
		override def compare(that: Pair): Int = {
			this.pair._1 compareTo that.pair._1 match {
				case 0 => PokerHand.compareByHighCard(this.hand -- this.pair, that.hand -- that.pair)
				case i => i
			}
		}
	}

	object Pair {
		def unapply(hand: Hand): Option[Pair] = {
			val pairs = hand.pairs
			if (pairs.size == 1) {
				Some(new Pair(hand, pairs.head))
			} else {
				None
			}
		}
	}

	class HighCard(val hand: Hand) extends PokerHand[HighCard] {
		override def compare(that: HighCard): Int = compareByHighCard(that.hand)
	}

	object HighCard {
		def unapply(hand: Hand): Option[HighCard] = Option(new HighCard(hand))
	}

}