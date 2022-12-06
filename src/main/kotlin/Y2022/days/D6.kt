package main.kotlin.Y2022.days

import common.Day

class D6 : Day {
    override fun execute(input: List<String>, part: Int): String {
        val datastream = input.first()
        val distinctCharacterCount = if (part == 1) 4 else 14
        datastream.drop(distinctCharacterCount-1).foldIndexed(datastream.take(distinctCharacterCount-1)) { i, last, current ->
            if ((last + current).toSet().size == distinctCharacterCount) {
                return "${i+distinctCharacterCount}"
            }
            last.drop(1) + current
        }
        return "None found"
    }
}