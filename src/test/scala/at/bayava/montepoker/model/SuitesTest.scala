package at.bayava.montepoker.model

import at.bayava.montepoker.BaseTest
import at.bayava.montepoker.model.Suites._

/**
	* Created by philba on 5/22/17.
	*/
class SuitesTest extends BaseTest {

	describe("Suites") {
		val suites = Table(("string", "suite"),
			("C", Clubs),
			("D", Diamonds),
			("H", Hearts),
			("S", Spades)
		)
		forEvery(suites) { (s: String, suite: Suite) =>
			it(s"should return $suite for input $s") {
				Suites(s) shouldBe suite
			}
		}
	}

}
