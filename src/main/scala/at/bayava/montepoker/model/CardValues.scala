package at.bayava.montepoker.model

/**
	* Created by philba on 5/22/17.
	*/
object CardValues {

	sealed trait CardValue extends Ordered[CardValue] {
		def value: Int

		override def toString: String = getClass.getSimpleName
	}

	sealed class BaseCardValue(i: Int, s: String) extends CardValue {
		def matches(s: String): Boolean = this.s == s

		override def value: Int = i

		override def compare(that: CardValue): Int = value compareTo that.value
	}

	object Ace extends BaseCardValue(14, "A")

	object King extends BaseCardValue(13, "K")

	object Queen extends BaseCardValue(12, "Q")

	object Jack extends BaseCardValue(11, "J")

	object Ten extends BaseCardValue(10, "10")

	object Nine extends BaseCardValue(9, "9")

	object Eight extends BaseCardValue(8, "8")

	object Seven extends BaseCardValue(7, "7")

	object Six extends BaseCardValue(6, "6")

	object Five extends BaseCardValue(5, "5")

	object Four extends BaseCardValue(4, "4")

	object Three extends BaseCardValue(3, "3")

	object Two extends BaseCardValue(2, "2")


	private val cardValues = initSubclasses

	private def initSubclasses = {
		import scala.reflect.runtime.{universe => ru}
		val tpe = ru.typeOf[BaseCardValue]
		val clazz = tpe.typeSymbol.asClass
		clazz.knownDirectSubclasses.map(_.asInstanceOf[BaseCardValue])
	}

	def apply(s: String): CardValue = {
		cardValues.find(_.matches(s)).get
	}

}
