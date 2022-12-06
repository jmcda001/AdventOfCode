package common

interface Day {
    fun execute(input: List<String>, part: Int = 1): String
}

class IncompleteDay : Day {
    override fun execute(input: List<String>, part: Int): String {
        TODO("Not yet implemented")
    }
}