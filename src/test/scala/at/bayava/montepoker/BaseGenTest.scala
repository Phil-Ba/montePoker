package at.bayava.montepoker

import at.bayava.montepoker.model.{Card, CardValues, Hand, Suites}
import org.scalacheck.{Gen, Shrink}
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FunSpec, Matchers}

import scala.util.Random

/**
	* Created by philba on 5/22/17.
	*/
trait BaseGenTest extends FunSpec with Matchers with GeneratorDrivenPropertyChecks {

	implicit val noShrinkHand: Shrink[Hand] = Shrink.shrinkAny
	implicit val noShrinkSeqCard: Shrink[Seq[Card]] = Shrink.shrinkAny
	implicit val noShrinkCard: Shrink[Card] = Shrink.shrinkAny

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

	val nOfCardsGen = Gen.pick(_: Int, Random.shuffle(cardStack))

	val nOfCardsGenNoDuplicates = nOfCardsGen(_: Int) suchThat (_.groupBy(_.value)
		.mapValues(_.length)
		.values
		.max == 1)

	val nOfCardsGenNoDuplicates2 = nOfCardsGen(_: Int).withFilter(_.groupBy(_.value)
		.mapValues(_.length)
		.values
		.max == 1)

	val handOfNGen = nOfCardsGen(_: Int).map(new Hand(_))

	val handOfNGenNoDuplicates = nOfCardsGenNoDuplicates(_: Int).map(new Hand(_))

	val handGen = handOfNGen(5)
}