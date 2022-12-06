package main.kotlin.Y2022

import common.IncompleteDay
import main.kotlin.Y2022.days.*
import main.kotlin.common.Year

class Y2022 : Year {
    override fun run(day: Int, part: Int, input: List<String>): String {
        val completedDays = mapOf(
            1 to D1(),
            2 to D2(),
            3 to Day3(),
            4 to D4(),
            5 to D5(),
        )
        return completedDays.getOrDefault(day, IncompleteDay()).execute(input, part)
    }
}