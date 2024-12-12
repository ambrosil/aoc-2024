package utils

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/input/$name.txt").readText().trim().lines()

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

fun List<String>.matrix(): MutableMap<Point, Int> {
    val matrix = mutableMapOf<Point, Int>()
    this.forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
            matrix[Point(x, y)] = c.digitToInt()
        }
    }

    return matrix
}

fun List<String>.matrixAsChars(): MutableMap<Point, Char> {
    val matrix = mutableMapOf<Point, Char>()
    this.forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
            matrix[Point(x, y)] = c
        }
    }

    return matrix
}