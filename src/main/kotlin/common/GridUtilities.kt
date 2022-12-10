package main.kotlin.common

enum class CardinalDirection(val dx: Int, val dy: Int) {
    NORTH(0, -1),
    EAST(1, 0),
    SOUTH(0, 1),
    WEST(-1, 0),
    NE(1, -1),
    NW(-1, -1),
    SE(1, 1),
    SW(-1, 1);

    override fun toString(): String {
        return "($dx, $dy)"
    }
}
data class Location(val x: Int, val y: Int) {
    operator fun plus(direction: CardinalDirection): Location {
        return Location(x + direction.dx, y + direction.dy)
    }

    override fun toString(): String {
        return "($x, $y)"
    }
}

fun <T, R>List<List<T>>.foldGridIndexed(initial: R, custom: (Location, R, T)->R): R {
    return foldIndexed(initial) { y, soFar: R, row: List<T> ->
        row.foldIndexed(soFar) { x, local: R, value: T ->
            val location = Location(x, y)
            custom(location, local, value)
        }
    }
}
fun <T>List<List<T>>.forEachLocation(custom: (Location, T)->Unit) {
    forEachIndexed { y, row: List<T> ->
        row.forEachIndexed { x, value: T ->
            val location = Location(x, y)
            custom(location, value)
        }
    }
}
fun <T>List<List<T>>.isInbounds(location: Location): Boolean {
    return location.x >= 0 && location.y >= 0 && location.x < get(location.y).size && location.y < size
}
fun <T>List<List<T>>.isEdge(location: Location): Boolean {
    return location.x == 0 || location.y == 0 || location.x == get(location.y).size-1 || location.y == size-1
}
fun <T>List<List<T>>.atLocationOrNull(location: Location): T? = getOrNull(location.y)?.getOrNull(location.x)
fun <T>List<List<T>>.atLocation(location: Location): T = get(location.y)[location.x]
fun <T>List<List<T>>.print(custom: (T) -> String) {
    forEach { row ->
        row.forEach {
            print(" ${custom(it)} ")
        }
        println()
    }
}

fun <T>buildGrid(input: List<String>, custom: (String)->List<T>): List<List<T>> {
    return input.map {
        custom(it)
    }
}