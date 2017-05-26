package at.bayava.montepoker.model

import at.bayava.montepoker.BaseGenTest
import at.bayava.montepoker.CardGens._
import org.scalacheck.Gen

/**
	*
	*/
class HandTest extends BaseGenTest {

	describe("Hand") {
		it("apply should create the right hand") {
			val cards = Gen.listOfN(5, cardGen)

			forAll(cards) { cards => {
				val cardList = cards.map(_._3)
				Hand(cards.map(c => c._1 + c._2).mkString(" ")).cards should contain theSameElementsAs cardList
			}
			}
		}

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

			it("should return an empty list if no duplicates") {
				forAll(handOfNGenNoDuplicates(5)) { np => {
					np.pairs shouldBe empty
				}
				}
			}
		}
	}


}
