import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Reads lines from the given input txt file and returns them as a single string.
 */
fun readRawInput(name: String) = Path("src/$name.txt")
    .readLines().joinToString(separator = "\n")

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

operator fun String.component1() = this[0]

operator fun String.component2() = this[1]

operator fun String.component3() = this[2]

operator fun String.component4() = this[3]