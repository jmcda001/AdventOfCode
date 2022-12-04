
fun main() {
    val days = listOf(
        IncompleteDay(),
        IncompleteDay(),
        IncompleteDay(),
        Day4()

    )
    days[3].execute("input.txt")
}