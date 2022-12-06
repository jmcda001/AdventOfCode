package main.kotlin.Y2022.days

import common.Day

class D1 : Day {
    data class Elf(val items: List<Int>) {
        val totalCalories = items.sum()
    }

    private fun List<String>.toElves() = sequence<Elf> {
        var allItems = this@toElves
        while (allItems.isNotEmpty()) {
            val items = allItems.takeWhile { it.isNotEmpty() }
            yield(Elf(items.map { it.toInt() }))
            allItems = allItems.drop(items.size + 1)
        }
    }
    override fun execute(input: List<String>, part: Int): String {
        return if (part == 1) {
            input.toElves().maxOf { it.totalCalories }.toString()
        } else {
            input.toElves().sortedBy { it.totalCalories }.toList().takeLast(3).sumOf { it.totalCalories }.toString()
        }
    }
}