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
	//	implicit override val generatorDrivenConfig =		new PropertyCheckConfiguration(maxDiscardedFactor = 10.0)

	implicit val noShrinkHand: Shrink[Hand] = Shrink.shrinkAny
	implicit val noShrinkSeqCard: Shrink[Seq[Card]] = Shrink.shrinkAny
	implicit val noShrinkCard: Shrink[Card] = Shrink.shrinkAny

}

object CardGens {

	val cardStack: Seq[Card] = for {
		cv <- CardValues.values.toSeq
		cs <- Suites.values.toSeq
	} yield {
		Card(cv, cs)
	}

	val nUniqueValues = Gen.pick(_: Int, Random.shuffle(CardValues.values.toIterable))

	val nUniqueSuites = Gen.pick(_: Int, Random.shuffle(Suites.values.toIterable))

	val cardValueGen: Gen[(String, CardValues.Value)] = Gen.oneOf(
		((2 to 10).map(_.toString) :+ "J" :+ "Q" :+ "K" :+ "A")
			.map(cv => (cv, CardValues.withName(cv)))
	)

	val cardSuitGen: Gen[(String, Suites.Value)] = Gen.oneOf(
		("C" :: "D" :: "H" :: "S" :: Nil)
			.map(cs => (cs, Suites.withName(cs)))
	)

	val cardGen: Gen[(String, String, Card)] = for {
		cv <- cardValueGen
		cs <- cardSuitGen
	} yield {
		(cv._1, cs._1, Card(cv._2, cs._2))
	}

	val simpleCardGen: Gen[Card] = cardGen.map(_._3)

	val pairGen: Gen[(Card, Card)] = (for {
		c <- simpleCardGen
		s <- cardSuitGen
	} yield {
		(c, new Card(c.value, s._2))
	}) suchThat (cp => cp._1.suite != cp._2.suite)

	val nOfCardsGen: (Int) => Gen[Seq[Card]] = Gen.pick(_: Int, Random.shuffle(cardStack))

	val nOfCardsGenNoDuplicates: (Int) => Gen[Seq[Card]] = nOfCardsGen(_: Int) suchThat (_.groupBy(_.value)
		.mapValues(_.length)
		.values
		.max == 1)

	val handOfNGen: (Int) => Gen[Hand] = nOfCardsGen(_: Int).map(new Hand(_))

	def handOfNGenNoDuplicatesWithoutValues(n: Int, value: CardValues.Value*): Gen[Hand] = {
		val reducedStack = Random.shuffle(cardStack)
			.groupBy(_.value)
			.map(_._2.head)
			.filterNot(c => value.contains(c.value))
		Gen.pick(n, reducedStack).map(new Hand(_))
	}

	val handOfNGenNoDuplicates: (Int) => Gen[Hand] = nOfCardsGenNoDuplicates(_: Int).map(new Hand(_))

	val handGen: Gen[Hand] = handOfNGen(5)

	val pairHand: Gen[(Hand, (Card, Card))] = for {
		p <- pairGen
		h <- handOfNGenNoDuplicatesWithoutValues(3, p._1.value)
	} yield {
		(h + p._1 + p._2, p)
	}
}