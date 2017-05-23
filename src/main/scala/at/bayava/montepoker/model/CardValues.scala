package at.bayava.montepoker.model

import at.bayava.montepoker.model.CardValues.CardValue

/**
	* Created by philba on 5/22/17.
	*/
object CardValues extends Enumeration {

	type CardValue = Value

	val Two = Value(2, "2")
	val Three = Value(3, "3")
	val Four = Value(4, "4")
	val Five = Value(5, "5")
	val Six = Value(6, "6")
	val Seven = Value(7, "7")
	val Eight = Value(8, "8")
	val Nine = Value(9, "9")
	val Ten = Value(10, "10")
	val Jack = Value(11, "J")
	val Queen = Value(12, "Q")
	val King = Value(13, "K")
	val Ace = Value(14, "A")

}
