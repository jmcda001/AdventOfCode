package main.kotlin.Y2022.days

import common.Day

class D2 : Day {
    enum class Hand(val score: Int) { ROCK(1), PAPER(2), SCISSORS(3) }

    enum class Outcome(val score: Int) { WIN(6), TIE(3), LOSE(0) }

    private val elfHands = mapOf(
        "A" to Hand.ROCK,
        "B" to Hand.PAPER,
        "C" to Hand.SCISSORS,
    )

    private val yourHands = mapOf(
        "X" to Hand.ROCK,
        "Y" to Hand.PAPER,
        "Z" to Hand.SCISSORS,
    )
    private val outcomes = mapOf(
        "X" to Outcome.LOSE,
        "Y" to Outcome.TIE,
        "Z" to Outcome.WIN,
    )
    private val strategyGuide = Pair(yourHands, outcomes)

    data class Round(val elf: Hand, val you: Hand, val outcome: Outcome) {
        val score
            get() = you.score + outcome.score

        companion object {
            fun fromHands(elf: Hand, you: Hand): Round {
                val outcome = when {
                    elf == Hand.SCISSORS && you == Hand.SCISSORS -> Outcome.TIE
                    elf == Hand.SCISSORS && you == Hand.ROCK -> Outcome.WIN
                    elf == Hand.SCISSORS && you == Hand.PAPER -> Outcome.LOSE
                    elf == Hand.ROCK && you == Hand.SCISSORS -> Outcome.LOSE
                    elf == Hand.ROCK && you == Hand.ROCK -> Outcome.TIE
                    elf == Hand.ROCK && you == Hand.PAPER -> Outcome.WIN
                    elf == Hand.PAPER && you == Hand.SCISSORS -> Outcome.WIN
                    elf == Hand.PAPER && you == Hand.ROCK -> Outcome.LOSE
                    elf == Hand.PAPER && you == Hand.PAPER -> Outcome.TIE
                    else -> Outcome.TIE
                }
                return Round(elf = elf, you = you, outcome = outcome)
            }
            fun fromOutcome(elf: Hand, outcome: Outcome): Round {
                val yourHand = when {
                    elf == Hand.SCISSORS && outcome == Outcome.TIE -> Hand.SCISSORS
                    elf == Hand.SCISSORS && outcome == Outcome.WIN -> Hand.ROCK
                    elf == Hand.SCISSORS && outcome == Outcome.LOSE -> Hand.PAPER
                    elf == Hand.ROCK && outcome == Outcome.LOSE -> Hand.SCISSORS
                    elf == Hand.ROCK && outcome == Outcome.TIE -> Hand.ROCK
                    elf == Hand.ROCK && outcome == Outcome.WIN -> Hand.PAPER
                    elf == Hand.PAPER && outcome == Outcome.LOSE -> Hand.ROCK
                    elf == Hand.PAPER && outcome == Outcome.WIN -> Hand.SCISSORS
                    elf == Hand.PAPER && outcome == Outcome.TIE -> Hand.PAPER
                    else -> Hand.PAPER
                }
                return Round(elf = elf, you = yourHand, outcome = outcome)
            }
        }
    }
    override fun execute(input: List<String>, part: Int): String {
        return input.sumOf { match ->
            match.split(" ").let {
                if (part == 1) {
                    Round.fromHands(elf = elfHands[it[0]]!!, you = strategyGuide.first[it[1]]!!)
                } else {
                    Round.fromOutcome(elf = elfHands[it[0]]!!, outcome = strategyGuide.second[it[1]]!!)
                }
            }.score
        }.toString()
    }
}