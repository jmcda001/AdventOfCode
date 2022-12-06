package main.kotlin.Y2022.days

import common.Day

class D5 : Day {
    private val moveRegex = "move (?<count>\\d+) from (?<src>\\d) to (?<sink>\\d)".toRegex()

    data class Move(val crateCount: Int, val startStack: Int, val endStack: Int) {
        override fun toString(): String {
            return "Move $crateCount from $startStack to $endStack"
        }
    }

    override fun execute(input: List<String>, part: Int): String {
        val crates = input.takeWhile { it.trimStart().startsWith("[") }
        val stackNumbers = input.drop(crates.size).first()
        val stacks = List(stackNumbers.trim().last().digitToInt()+1) { ArrayDeque<Char>() }
        crates.forEach { crateRow ->
            crateRow.forEachIndexed { index, crate ->
                if (crate.isLetter()) {
                    stacks[stackNumbers[index].digitToInt()].addFirst(crate)
                }
            }
        }
        input.drop(crates.size + 1).forEach { move ->
            moveRegex.matchEntire(move)?.groups?.let {
                stacks.executeMove(
                    move = Move(
                        crateCount = it["count"]!!.value.toInt(),
                        startStack = it["src"]!!.value.toInt(),
                        endStack = it["sink"]!!.value.toInt(),
                    ),
                    part = part
                )
            }
        }

        return stacks.drop(1).map { it.last() }.joinToString("")
    }

    private fun List<ArrayDeque<Char>>.executeMove(move: Move, part: Int = 1) {
        if (part == 1) {
            (1..move.crateCount).forEach { _ -> get(move.endStack).add(get(move.startStack).removeLast()) }
        } else {
            get(move.endStack).addAll(get(move.startStack).takeLast(move.crateCount))
            (1..move.crateCount).forEach { _ -> get(move.startStack).removeLast() }
        }
    }

    private fun List<ArrayDeque<Char>>.toDisplayString(): String {
        val stacksLabels = List(size) { it }.joinToString("   ")
        val tallest = maxOf { it.size }
        return (tallest downTo 0).joinToString("\n") { height ->
            joinToString(" ") { stack -> stack.getOrNull(height)?.let { "[$it]" } ?: "   " }
        } + "\n " + stacksLabels
    }
}