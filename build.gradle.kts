plugins {
    kotlin("jvm") version "1.9.20"
}

repositories {
    mavenCentral()
}

tasks {
    sourceSets {
        main {
            kotlin.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "8.4"
    }

    task("generateNextDay") {
        doLast {
            val prevDayNum = fileTree("$projectDir/src").matching {
                include("Day*.kt")
            }.maxOf {
                val (prevDayNum) = Regex("Day(\\d\\d)").find(it.name)!!.destructured
                prevDayNum.toInt()
            }
            val newDayNum = String.format("%02d", prevDayNum + 1)
            File("$projectDir/src","Day$newDayNum.txt").writeText("")
            File("$projectDir/src","Day${newDayNum}_test.txt").writeText("")
            File("$projectDir/src", "Day$newDayNum.kt").writeText(
                """fun main() {
    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day${newDayNum}_test")
    check(part1(testInput) == 0)

    val input = readInput("Day$newDayNum")
    part1(input).println()
    part2(input).println()
}
"""
            )
        }
    }
}