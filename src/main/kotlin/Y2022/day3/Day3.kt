package main.kotlin.Y2022.day3

import common.Day
class Day3 : Day {
    private fun Char.priority() = if (isUpperCase()) { code - 38 } else { code - 96 }

    override fun execute(input: List<String>, part: Int): String {
        return if (part == 1) {
            input.sumOf { rucksack ->
                val compartment1 = rucksack.take(rucksack.length / 2).toSet()
                val compartment2 = rucksack.takeLast(rucksack.length / 2).toSet()
                compartment1.intersect(compartment2).first().priority()
            }.toString()
        } else {
            input.chunked(3).sumOf {
                val elf1 = it[0].toSet()
                val elf2 = it[1].toSet()
                val elf3 = it[2].toSet()
                elf1.intersect(elf2).intersect(elf3).first().priority()
            }.toString()
        }
    }
}