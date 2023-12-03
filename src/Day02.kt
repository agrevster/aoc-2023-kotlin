data class CubeHand(val red: Int, val green: Int, val blue: Int)

class Game(gameLine: String) {
    val id: Int

    private val cubeHands: List<CubeHand>

    init {
        val tempCubes = mutableListOf<CubeHand>()
        val splitLine = gameLine.split(": ")

        id = splitLine[0].filter { it.isDigit() }.toInt()

        splitLine[1].split(";").forEach { hand ->
            val colors = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)

            hand.split(",").forEach { colorCount ->
                val (count, color) = colorCount.trimIndent().split(" ")
                colors[color] = count.toInt()
            }

            tempCubes.add(CubeHand(colors["red"]!!, colors["green"]!!, colors["blue"]!!))
        }
        cubeHands = tempCubes
    }

    fun isPossibleWithCubeCount(cubes: CubeHand): Boolean =
        cubeHands.none { (it.red > cubes.red) || (it.green > cubes.green) || (it.blue > cubes.blue) }

    private val largestCubeCounts: CubeHand =
        CubeHand(cubeHands.maxOf { it.red }, cubeHands.maxOf { it.green }, cubeHands.maxOf { it.blue })

    val power = largestCubeCounts.red * largestCubeCounts.green * largestCubeCounts.blue

    override fun toString(): String {
        return "Game(id=$id, cubeHands=$cubeHands)"
    }

}

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { Game(it) }.filter { it.isPossibleWithCubeCount(CubeHand(red = 12, green = 13, blue = 14)) }
            .sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        return input.map { Game(it) }
            .sumOf { it.power }
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")

    part1(input).println()
    part2(input).println()
}