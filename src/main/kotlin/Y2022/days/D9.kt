package main.kotlin.Y2022.days

import common.Day
import main.kotlin.common.CardinalDirection
import main.kotlin.common.Location

class D9 : Day {
    override fun execute(input: List<String>, part: Int): String {
        val knotCount = if (part == 1) 2 else 10
        var knots = List(knotCount) { Location(0, 0) }
        val visited = input.map { movement ->
            val (direction, amount) = movement.split(' ').let { (d, a) ->
                val dir = when (d) {
                    "D" -> CardinalDirection.NORTH
                    "U" -> CardinalDirection.SOUTH
                    "R" -> CardinalDirection.EAST
                    "L" -> CardinalDirection.WEST
                    else -> throw IllegalArgumentException("Unknown direction $d")
                }
                Pair(dir, a.toInt())
            }
            (1..amount).map {  _ ->
                knots = knots.move(direction)
                knots.last()
            }
        }

        return visited.flatten().toSet().size.toString()
    }
    private fun List<Location>.move(direction: CardinalDirection): List<Location> {
        val nextKnots = mutableListOf(first() + direction)
        drop(1).forEachIndexed { i, knot ->
            nextKnots.add(knot.catchUp(nextKnots[i]))
        }
        return nextKnots
    }
    private fun Location.catchUp(head: Location): Location {
        return when {
            head == this -> this
            CardinalDirection.values().any { head + it == this } -> this
            this.x == head.x -> Location(x, y + if (head.y > y) 1 else -1)
            this.y == head.y -> Location( x + if (head.x > x) 1 else -1, y)
            else -> {
                Location(x + if (head.x > x) 1 else -1, y + if (head.y > y) 1 else -1)
            }
        }
    }
}