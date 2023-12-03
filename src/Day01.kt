val digitWords = mapOf<String, Int> (
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9,
)

fun digitWordsToDigits(string: String): String = buildString {
    string.forEachIndexed{ i, c ->
        if (c.isDigit()) append(c)
        for ((digitWord, digitInt) in digitWords) {
            if (string.substring(i).startsWith(digitWord)) append(digitInt)
        }
    }
}

fun main() {
    fun part1(input: String): Int {
        return input.split("\n")
            .map { it.filter {text -> text.isDigit() } }
            .map { "${it.first()}${it.last()}"}
            .sumOf { it.toInt() }
    }

    fun part2(input: String): Int {
        return input.split("\n")
            .map { digitWordsToDigits(it) }
            .map { it.filter {text -> text.isDigit() } }
            .map { "${it.first()}${it.last()}"}
            .sumOf { it.toInt() }
    }

    val testInput = readRawInput("Day01_test")
    check(part2(testInput) == 281)

    val input = readRawInput("Day01")
    part1(input).println()
    part2(input).println()

}