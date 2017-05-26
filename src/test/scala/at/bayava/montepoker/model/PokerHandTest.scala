package at.bayava.montepoker.model

import at.bayava.montepoker.BaseTest
import at.bayava.montepoker.model.PokerHands.PokerHand

/**
	*
	*/
class PokerHandTest extends BaseTest {

	describe("The PokerHand object") {

		describe("compare by highCard") {
			val testdata = Table(("h1", "h2", "result"),
				(Hand("AD"), Hand("AD"), 0),
				(Hand("5D"), Hand("AD"), -1),
				(Hand("5D QS"), Hand("5D KS"), -1),
				(Hand("2D QS AD"), Hand("QS AD 3S"), -1),
				(Hand("3S 4S 5S 6S"), Hand("2S 4S 5S 6S"), 1)
			)
			forEvery(testdata) { (h1: Hand, h2, r) => {
				it(s"should return the right result for $h1 and $h2") {
					PokerHand.compareByHighCard(h1, h2) shouldBe r
					PokerHand.compareByHighCard(h2, h1) shouldBe r * -1
				}
			}
			}
		}
	}

}