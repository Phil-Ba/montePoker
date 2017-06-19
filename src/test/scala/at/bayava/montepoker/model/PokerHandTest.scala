package at.bayava.montepoker.model

import at.bayava.montepoker.CardGens._
import at.bayava.montepoker.model.PokerHands.{HighCard, Pair, PokerHand, ThreeOfAKind, TwoPair}
import at.bayava.montepoker.{BaseGenTest, BaseTest}

import scala.util.Random

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

		//HIGHCARD
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

		//PAIR
		describe("The pair class") {
			describe("unapply method") {
				it("should return Some for all hands containing a pair") {
					forAll(pairHand) { ph => {
						val hand = ph._1
						val pair = ph._2

						val result = Pair.unapply(hand)

						result should not be empty
						result.get.pair.productIterator.toSeq should contain theSameElementsAs pair.productIterator.toSeq
					}
					}
				}
				it("should return None for all hands not containing a pair") {
					forAll(handOfNGenNoDuplicates(5)) { nph => {

						val result = Pair.unapply(nph)

						result shouldBe empty
					}
					}
				}
			}

			describe("the compare method") {
				it("should compare first by the highest pair") {
					forAll(pairHand, pairHand) { (ph1, ph2) =>
						whenever(ph1._2._1.value != ph2._2._1.value) {
							val p1 = ph1._2
							val p2 = ph2._2
							val expected = p1._1 compareTo p2._1

							val result = new Pair(ph1._1, ph1._2) compareTo new Pair(ph2._1, ph2._2)

							result shouldBe expected
						}
					}
				}
				it("and then by the highest card") {
					forAll(pairGen, simpleCardGen, simpleCardGen, simpleCardGen, simpleCardGen) { (pair, c1, c2, c3, c4) =>
						whenever(c3 < c4) {
							val params1 = Random.shuffle(Seq(pair._1, pair._2, c1, c2, c3))
							val params2 = Random.shuffle(Seq(pair._1, pair._2, c1, c2, c4))
							val lowPair = new Pair(new Hand(params1), pair)
							val highPair = new Pair(new Hand(params2), pair)

							lowPair compareTo highPair shouldBe -1
							highPair compareTo lowPair shouldBe 1
						}
					}
				}
			}
		}

		//TWOPAIR
		describe("The TwoPair class") {
			it("unapply method should return None if hand doesnt contain 2 pairs") {
				forAll(handGen) { h =>
					whenever(h.pairs.size < 2) {
						TwoPair.unapply(h) shouldBe empty
					}
				}
			}
			it("unapply method should return Some if hand contains 2 pairs") {
				forAll(pairGen, pairGen, simpleCardGen) { (p1, p2, c) =>
					whenever(p1._1.value != p2._1.value && c.value != p1._1.value && c.value != p2._1.value) {
						val (p11, p12) = p1
						val (p21, p22) = p2
						val params = Random.shuffle(Seq(p11, p12, p21, p22, c))

						val result = TwoPair.unapply(new Hand(params))

						result should not be empty
						result.get.pairs.map(_._1.value) should contain allElementsOf Seq(p1, p2).map(_._1.value)
					}
				}
			}
			describe("compare method") {
				it("should pick the highest pair when all ") {

				}
			}
		}

		//THREE OF A KIND
		describe("Three of a kind") {
			it("unapply method should return Some if hand contains a triple") {
				forAll(tripleHandGen) { (triple) =>

					val result = ThreeOfAKind.unapply(triple)

					result should not be empty
				}
			}
	}
	}
}