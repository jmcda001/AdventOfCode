package main.kotlin.Y2022

import common.IncompleteDay
import main.kotlin.Y2022.Day4.Day4
import main.kotlin.common.Year

class Y2022 : Year {
    override fun run(day: Int, part: Int, input: List<String>): Int {
        val completedDays = mapOf(
            4 to Day4()
        )
        return completedDays.getOrDefault(day, IncompleteDay()).execute(input, part)
    }
}