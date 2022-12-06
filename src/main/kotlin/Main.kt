package main.kotlin

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.check
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.int
import main.kotlin.common.Year
import main.kotlin.Y2022.Y2022
import main.kotlin.common.Years
import java.lang.IllegalArgumentException
import java.time.LocalDate

class AdventOfCode : CliktCommand(name = "aoc") {
    private val year: Int by option().int().default(LocalDate.now().year)
    private val day: Int by option(help="Day to run").int().default(LocalDate.now().dayOfMonth)
    private val part: Int by option(help="Part to run").int().default(1).check { it in 1..2 }
    private val both: Boolean by option(help="Run both parts").flag("--both", default = false)
    private val input by argument(help="Input file").file(mustExist = true, mustBeReadable = true)

    override fun run() {
        val year: Year = when (Years.values().find { it.year == year }) {
            Years.TWENTY_TWO -> Y2022()
            else -> {
                throw IllegalArgumentException("$year has not been setup")
            }
        }
        val parsedInput: List<String> = input.readLines()
        if (both) {
            val part1 = year.run(day, 1, parsedInput)
            val part2 = year.run(day, 2, parsedInput)
            echo("$year Day $day part $part: $part1")
            echo("$year Day $day part $part: $part2")
        } else {
            val result = year.run(day, part, parsedInput)
            echo("$year Day $day part $part: $result")
        }
    }
}

fun main(args: Array<String>) = AdventOfCode().main(args)