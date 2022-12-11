package main.kotlin.Y2022.days

import common.Day

class D10 : Day {
    enum class Command { NOOP, ADDX }
    data class Instruction(val command: Command, val argument: Int? = null)
    data class CpuStatus(val cycle: Int = 1, val xReg: Int = 1) {
        fun process(instr: Instruction): List<CpuStatus> {
            return when (instr.command) {
                Command.NOOP -> listOf(copy(cycle = cycle + 1))
                Command.ADDX -> {
                    listOf(
                        copy(cycle = cycle + 1, xReg = xReg),
                        copy(cycle = cycle + 2, xReg = xReg + instr.argument!!)
                    )
                }
            }
        }
        fun signalStrength() = cycle * xReg
    }
    override fun execute(input: List<String>, part: Int): String {
        var cpuStatus = CpuStatus()
        val statuses = input.map { instruction ->
            val instr = instruction.split(' ').let {
                Instruction(Command.valueOf(it[0].uppercase()), it.getOrNull(1)?.toInt())
            }
            cpuStatus.process(instr).also { cpuStatus = it.last() }
        }.flatten()
        return if (part == 1 ) {
            val filterCycles = generateSequence(20) { it + 40 }.takeWhile { it <= statuses.last().cycle }
            statuses.filter { it.cycle in filterCycles }.sumOf { it.signalStrength() }.toString()
        } else {
            val crt = List(6) { r ->
                List(40) { c ->
                    val cycle = (c + (r * 40)) + 1
                    val sprite = statuses.find { it.cycle == cycle }?.let { listOf(it.xReg - 1, it.xReg, it.xReg + 1) }
                        ?: listOf(0, 1, 2)
                    if ((c) in sprite) {
                        "#"
                    } else {
                        "."
                    }
                }
            }
            val display = crt.joinToString("\n") { row -> row.joinToString("") { it } }
            "\n$display\n8 capital letters formed above"
        }
    }
}
