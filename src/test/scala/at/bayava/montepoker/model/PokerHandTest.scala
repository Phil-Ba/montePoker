package at.bayava.montepoker.model

import at.bayava.montepoker.CardGens._
import at.bayava.montepoker.model.PokerHands.{HighCard, Pair, PokerHand}
import at.bayava.montepoker.{BaseGenTest, BaseTest}

/**
	*
	*/
class PokerHandTest extends BaseTest with BaseGenTest {
	val simpleTestDate = Table(("h1", "h2", "result"),
		(Hand("AD"), Hand("AD"), 0),
		(Hand("5D"), Hand("AD"), -1),
		(Hand("5D QS"), Hand("5D KS"), -1),
		(Hand("2D QS AD"), Hand("QS AD 3S"), -1),
		(Hand("3S 4S 5S 6S"), Hand("2S 4S 5S 6S"), 1)
	)

	describe("The PokerHand object") {
		describe("compare by highCard") {
			forEvery(simpleTestDate) { (h1: Hand, h2, r) => {
				it(s"should return the right result for $h1 and $h2") {
					PokerHand.compareByHighCard(h1, h2) shouldBe r
					PokerHand.compareByHighCard(h2, h1) shouldBe r * -1
				}
			}
			}
			describe("compare") {
				forEvery(simpleTestDate) { (h1: Hand, h2, r) => {
					it(s"should return the right result for $h1 and $h2") {
						val hc1 = new HighCard(h1)
						val hc2 = new HighCard(h2)
						hc1.compare(hc2) shouldBe r
						hc2.compare(hc1) shouldBe r * -1
					}
				}
				}
			}
		}

		describe("The HighCard class") {
			describe("unapply") {
				it(s"should return some for all hands") {
					forAll(handGen) { (h: Hand) => {
						HighCard.unapply(h) should not be empty
					}
					}
				}
			}
		}

		describe("The pair class") {
			describe("unapply method") {
				it("should return Some for all hands containing a pair") {
					forAll(pairHand) { ph => {
						val hand = ph._1
						val pair = ph._2

						val result = Pair.unapply(hand)

						result should not be empty
						result.get.pair shouldBe pair
					}
					}
				}
			}
		}

	}
}
