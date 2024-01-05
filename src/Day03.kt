sealed class SchematicChar(lineNumber: Int)
data class PartNumber(val value: Int, private val range: IntRange, val lineNumber: Int) : SchematicChar(lineNumber) {
    val expandedRange = range.first - 1..range.last + 1
    val expandedLineNumber = lineNumber - 1..lineNumber + 1
}

data class Symbol(val value: Char, val location: Int, val lineNumber: Int) : SchematicChar(lineNumber)

fun parseSchematic(input: String): List<List<SchematicChar>> = buildList {
    input.lines().forEachIndexed { lineNumber: Int, line: String ->
        var parsingNumber = false
        val numberBuilder = StringBuilder()
        val endsWithNumber = line.last().isDigit()
        add(buildList {

            line.forEachIndexed { locationOnLine: Int, char: Char ->
                if (char.isDigit()) {
                    parsingNumber = true
                    numberBuilder.append(char)
                }

                if (!char.isDigit() && parsingNumber) {
                    parsingNumber = false
                    add(
                        PartNumber(
                            numberBuilder.toString().toInt(),
                            (locationOnLine - numberBuilder.length..<locationOnLine),
                            lineNumber
                        )
                    )
                    numberBuilder.clear()
                }

                if (endsWithNumber && locationOnLine == line.lastIndex) {
                    add(
                        PartNumber(
                            numberBuilder.toString().toInt(),
                            (line.length - numberBuilder.length..line.lastIndex),
                            lineNumber
                        )
                    )
                }

                if (Regex("[^\\d.]").matches(char.toString())) add(Symbol(char, locationOnLine, lineNumber))
            }
        })
    }
}

fun List<List<SchematicChar>>.validPartNumbers(): Set<PartNumber> = buildSet {
    windowed(2) { rows: List<List<SchematicChar>> ->
        val numbers = rows.flatten().filterIsInstance<PartNumber>()
        val symbols = rows.flatten().filterIsInstance<Symbol>()
        addAll(numbers.filter { number: PartNumber ->
            symbols.any { symbol: Symbol ->
                symbol.location in number.expandedRange
            }
        })
    }
}

fun List<SchematicChar>.gearRatios(): List<Int> {
    val numbers = this.filterIsInstance<PartNumber>()
    val gears = this.filterIsInstance<Symbol>().filter { it.value == '*' }
    return gears.map { gear ->
        numbers.filter { number ->
            number.expandedLineNumber.contains(gear.lineNumber) && number.expandedRange.contains(gear.location)
        }
    }.filter { it.size == 2 }
        .map { it[0].value * it[1].value }
}

fun main() {
    fun part1(input: String): Int = parseSchematic(input).validPartNumbers().sumOf { it.value }


    fun part2(input: String): Int  = parseSchematic(input).flatten().gearRatios().sum()


    val testInput = readRawInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readRawInput("Day03")

    part1(input).println()
    part2(input).println()
}