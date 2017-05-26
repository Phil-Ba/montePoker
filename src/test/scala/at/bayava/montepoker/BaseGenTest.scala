package at.bayava.montepoker

import at.bayava.montepoker.model.{Card, CardValues, Suites}
import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FunSpec, Matchers}

/**
	* Created by philba on 5/22/17.
	*/
trait BaseGenTest extends FunSpec with Matchers with GeneratorDrivenPropertyChecks {


}

object CardGens {

	val cardStack = for {
		cv <- CardValues.values.toSeq
		cs <- Suites.values.toSeq
	} yield {
		Card(cv, cs)
	}

	val cardValueGen = Gen.oneOf(
		((2 to 10).map(_.toString) :+ "J" :+ "Q" :+ "K" :+ "A")
			.map(cv => (cv, CardValues.withName(cv)))
	)

	val cardSuitGen = Gen.oneOf(
		("C" :: "D" :: "H" :: "S" :: Nil)
			.map(cs => (cs, Suites.withName(cs)))
	)

	val cardGen = for {
		cv <- cardValueGen
		cs <- cardSuitGen
	} yield {
		Card(cv._2, cs._2)
	}

	val pairGen = (for {
		c <- cardGen
		s <- cardSuitGen
	} yield {
		(c, new Card(c.value, s._2))
	}) suchThat (cp => cp._1.suite != cp._2.suite)

	val nOfCardsGen = Gen.pick(_: Int, cardStack)

	val handGen = nOfCardsGen(5)
}