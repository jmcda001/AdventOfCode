package main.kotlin.Y2022.Day4

import common.Day

class Day4 : Day {
    data class SectionAssignment(val elf1: Pair<Int, Int>, val elf2: Pair<Int, Int>) {
        private fun Pair<Int, Int>.contains(other: Pair<Int, Int>) = first <= other.first && second >= other.second
        fun isContained() = elf1.contains(elf2) || elf2.contains(elf1)
        fun isOverlapping() = (elf1.first <= elf2.second && elf1.second >= elf2.first)
    }

    override fun execute(input: List<String>, part: Int): String {
        val allAssignments = input.map { it.toSectionAssignments() }
        return allAssignments.fold(0) { totalOverlapping, current ->
            if (part == 1) {
                totalOverlapping + if (current.isContained()) { 1 } else { 0 }
            } else {
                totalOverlapping + if (current.isOverlapping()) { 1 } else { 0 }
            }
        }.toString()
    }

    private fun String.toSectionAssignments(): SectionAssignment {
        return split(',').let { elfDuties ->
            val elf1 = elfDuties[0].split('-').let { Pair(it[0].toInt(), it[1].toInt()) }
            val elf2 = elfDuties[1].split('-').let { Pair(it[0].toInt(), it[1].toInt()) }
            SectionAssignment(elf1 = elf1, elf2 = elf2)
        }
    }
}