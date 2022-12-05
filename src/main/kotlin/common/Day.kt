package common

interface Day {
    fun execute(input: List<String>, part: Int = 1): Int
}

class IncompleteDay : Day {
    override fun execute(input: List<String>, part: Int): Int {
        TODO("Not yet implemented")
    }
}