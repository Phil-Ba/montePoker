package at.bayava.montepoker.model

import at.bayava.montepoker.BaseTest

/**
	* Created by philba on 5/23/17.
	*/
class CardTest extends BaseTest {

	describe("Card apply") {
		val testData = Table(("input", "result"),
			("10S", new Card(CardValues.Ten, Suites.Spades)),
			("JD", new Card(CardValues.Jack, Suites.Diamonds)),
			("KH", new Card(CardValues.King, Suites.Hearts)),
			("2C", new Card(CardValues.Two, Suites.Clubs))
		)

		forEvery(testData) { (input, result) => {
			it(s"should return $result for $input") {
				Card(input) shouldBe result
			}
		}
		}

	}

}
