package at.bayava.montepoker.model

import at.bayava.montepoker.BaseTest

import scala.util.Random

/**
	* Created by philba on 5/22/17.
	*/
class CardValuesTest extends BaseTest {

	describe("Card values") {

		val values = Table(("input", "cardValue"),
			("10", CardValues.Ten),
			("9", CardValues.Nine),
			("Q", CardValues.Queen),
			("A", CardValues.Ace),
			("3", CardValues.Three)
		)
		forEvery(values) { (s, cardValue) =>
			it(s"should return $cardValue for input '$s'") {
				CardValues(s) shouldBe cardValue
			}
		}

		it("ordering should sort correctly") {
			val cardValues = Seq(CardValues.Ten, CardValues.Ace, CardValues.Two, CardValues.Jack, CardValues.Seven,
				CardValues.Ace)
			val sortedList = Random.shuffle(cardValues).sorted

			sortedList shouldBe Seq(CardValues.Two, CardValues.Seven, CardValues.Ten, CardValues.Jack, CardValues.Ace,
				CardValues.Ace)
		}

	}

}
