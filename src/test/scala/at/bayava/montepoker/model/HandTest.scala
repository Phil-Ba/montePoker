package at.bayava.montepoker.model

import at.bayava.montepoker.BaseGenTest
import at.bayava.montepoker.CardGens._
import org.scalacheck.Gen

import scala.util.Random

/**
	*
	*/
class HandTest extends BaseGenTest {

	val hand = handGen

	describe("Hand") {
		it("apply should create the right hand") {
			val cards = Gen.listOfN(5, cardGen)

			forAll(cards) { cards => {
				val cardList = cards.map(_._3)
				Hand(cards.map(c => c._1 + c._2).mkString(" ")).cards should contain theSameElementsAs cardList
			}
			}
		}

		it("+ should add a card to underlying card list") {
			forAll(hand, cardGen) { (h, c) => {
				val result = h + c._3
				result.cards should contain(c._3)
				result.cards should have size (h.cards.length + 1)
				result.cards should contain allElementsOf h.cards
			}
			}
		}

		it("- should remove an existing card to underlying card list") {
			forAll(hand) { (h) => {
				val toRemove = Random.shuffle(h.cards).head

				val result = h - toRemove

				result.cards should have size (h.cards.length - 1)
				result.cards should not contain toRemove
				result.cards should contain allElementsOf h.cards.filterNot(_ == toRemove)
			}
			}
		}

		it("- should not remove an non existing card to underlying card list") {
			forAll(hand, cardGen) { (h, c) =>
				whenever(h.cards.contains(c._3) == false) {
					val result = h - c._3

					result.cards should have size (h.cards.length)
					result.cards should not contain (c._3)
					result.cards should contain allElementsOf h.cards
				}
			}
		}

		it("-- should remove cards from underlying card list") {
			forAll(pairHand) { (ph) =>
				val hand = ph._1
				val pair = ph._2

				val result = hand --- pair

				result.cards should have size (hand.cards.length - 2)
				result.cards should not contain pair._1
				result.cards should not contain pair._2
				result.cards should contain allElementsOf hand.cards.filterNot(c => c == pair._1 || c == pair._2)
			}
		}

		it("highcard should return the maximum value") {
			forAll(hand) { hand => hand.highCard.value shouldBe hand.cards.maxBy(_.value).value }
		}

		describe("pairs") {
			it("should return the existing pair") {
				forAll(pairHand) { ph => {
					val pairs = ph._1.pairs
					pairs should contain oneOf(ph._2, ph._2.swap)
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
