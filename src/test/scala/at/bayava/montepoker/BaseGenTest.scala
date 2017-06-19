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

	val tripleHandGen: Gen[Hand] = for {
		suites <- nUniqueSuites(3)
		value <- nUniqueValues(1)
		remainingHand <- nCardsGenNoDuplicatesWithoutValues(2, value.head)
	} yield {
		val tripleCards = for (s <- suites) yield Card(value.head, s)
		new Hand(Random.shuffle(tripleCards ++ remainingHand))
	}

	val pairGen: Gen[(Card, Card)] = for {
		cv <- cardValueGen
		s <- nUniqueSuites(2)
	} yield {
		(new Card(cv._2, s.head), new Card(cv._2, s.last))
	}

	val nOfCardsGen: (Int) => Gen[Seq[Card]] = Gen.pick(_: Int, Random.shuffle(cardStack))

	def nCardsGenNoDuplicatesWithoutValues(n: Int, value: CardValues.Value*): Gen[Seq[Card]] = {
		val reducedStack = Random.shuffle(cardStack)
			.filterNot(c => value.contains(c.value))
			.groupBy(_.value)
			.map(_._2.head)
		Gen.pick(n, reducedStack)
	}

	val nOfCardsGenNoDuplicates: (Int) => Gen[Seq[Card]] = nCardsGenNoDuplicatesWithoutValues(_: Int)

	val handOfNGen: (Int) => Gen[Hand] = nOfCardsGen(_: Int).map(new Hand(_))

	def handOfNGenNoDuplicatesWithoutValues(n: Int, value: CardValues.Value*): Gen[Hand] = nCardsGenNoDuplicatesWithoutValues(n, value: _*).map(new Hand(_))

	val handOfNGenNoDuplicates: (Int) => Gen[Hand] = nOfCardsGenNoDuplicates(_: Int).map(new Hand(_))

	val handGen: Gen[Hand] = handOfNGen(5)

	val pairHand: Gen[(Hand, (Card, Card))] = for {
		p <- pairGen
		h <- handOfNGenNoDuplicatesWithoutValues(3, p._1.value)
	} yield {
		(h + p._1 + p._2, p)
	}
}