package at.bayava.montepoker.model

import at.bayava.montepoker.BaseGenTest
import at.bayava.montepoker.CardGens._

/**
	*
	*/
class HandTest extends BaseGenTest {

	describe("Hand") {
		describe("pairs") {
			it("should return the existing pair") {
				val pairHand = for {
					p <- pairGen
					h <- handOfNGenNoDuplicates(3)
					if h.cards.forall(_.value != p._1.value)
				} yield {
					(h + p._1 + p._2, p)
				}

				forAll(pairHand) { ph => {
					val pairs = ph._1.pairs
					pairs should contain(ph._2)
				}
				}
			}
		}
	}


}
