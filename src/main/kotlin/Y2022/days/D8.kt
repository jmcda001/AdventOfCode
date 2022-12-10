package main.kotlin.Y2022.days

import common.Day
import main.kotlin.common.*


class D8 : Day {
    val Directions = listOf(CardinalDirection.NORTH, CardinalDirection.SOUTH, CardinalDirection.WEST, CardinalDirection.EAST)
    private data class Tree(val height: Int) {
        val surroundingHeights = mutableMapOf<CardinalDirection, Int>()
        val surroundingVisibility = mutableMapOf<CardinalDirection, Int>()
        val isVisible: Boolean
            get() = surroundingHeights.size == 4 && surroundingHeights.any { (_, maxHeight) ->
                maxHeight < height
            }
        val scenicScore: Int
            get() = surroundingVisibility.toList().fold(1) { visibility, (_, distance) -> visibility * distance }
    }
    data class MaxHeightAndDistance(val maxHeight: Int, val distance: Int = 0)
    private fun List<List<Tree>>.findVisibilityDistance(location: Location, direction: CardinalDirection) {
        val tree = atLocation(location)
        var nextLocation = location + direction
        var nextTree = atLocationOrNull(nextLocation)
        var visibility = 0
        while (nextTree?.let { it.height < tree.height } == true) {
            visibility += 1
            nextLocation += direction
            nextTree = atLocationOrNull(nextLocation)
        }
        if (nextTree != null) { visibility += 1 }
        tree.surroundingVisibility[direction] = visibility
    }
    private fun List<List<Tree>>.findMaxHeight(location: Location, direction: CardinalDirection): Int {
        val currentTree = atLocationOrNull(location) ?: return 0
        currentTree.surroundingHeights[direction]?.let {
            return maxOf(currentTree.height, it)
        }

        return findMaxHeight(location + direction, direction).let { surroundingHeight ->
            currentTree.surroundingHeights[direction] = surroundingHeight
            maxOf(currentTree.height, surroundingHeight)
        }
    }
    override fun execute(input: List<String>, part: Int): String {
        val forest = buildGrid(input) { row -> row.map { Tree(it.digitToInt()) } }
        forest.forEachLocation { location, _ ->
            Directions.forEach { dir ->
                forest.findMaxHeight(location, dir)
                forest.findVisibilityDistance(location, dir)
            }
        }

        return if (part == 1) {
            forest.foldGridIndexed(0) { location, rowCount, tree ->
                rowCount + if (forest.isEdge(location) || tree.isVisible) { 1 } else { 0 }
            }.toString()
        } else {
            forest.flatten().maxOf { it.scenicScore }.toString()
        }
    }
}