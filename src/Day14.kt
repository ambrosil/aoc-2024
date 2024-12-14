import utils.println
import utils.readInput
import java.awt.Desktop
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import java.util.zip.GZIPOutputStream
import javax.imageio.ImageIO
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

fun main() {

    val W = 101
    val H = 103

    data class Pos(val x: Int, val y: Int)
    data class Bot(val p: Pos, val v: Pos) {
        fun step(t: Int): Pos {
            var x = (p.x + v.x * t) % W
            if (x < 0) x += W
            var y = (p.y + v.y * t) % H
            if (y < 0) y += H
            return Pos(x, y)
        }
    }

    infix fun List<Bot>.go(t: Int) = map { it.step(t) }

    fun showTree(pos: List<Pos>) {
        val points = mutableMapOf<Pos, Int>()
        val pixels = BufferedImage(W, H, BufferedImage.TYPE_BYTE_BINARY);
        pos.forEach { points[it] = 0 }

        for (y in 0..<H) {
            for (x in 0..<W) {
                val color = points.getOrDefault(Pos(x, y), 0xFFFFFF);
                pixels.setRGB(x, y, color);
            }
        }

        val f = File("tree.png")
        ImageIO.write(pixels, "png", f);
        Desktop.getDesktop().open(f)
    }

    fun entropy(pos: List<Pos>): Double {
        val bits = BitSet(W * H)
        pos.forEach { p -> bits.set((p.y * W + p.x)) }
        val arr = bits.toByteArray()

        val compressed = ByteArrayOutputStream().use { outBytes ->
            GZIPOutputStream(outBytes).use { gz -> gz.write(arr) }
            outBytes.size()
        }

        return compressed.toDouble()
    }

    fun parse(input: List<String>) =
        input
            .map { it.split(" ") }
            .map {
                val (px, py) = it[0].substringAfter("=").split(",")
                val (vx, vy) = it[1].substringAfter("=").split(",")
                Bot(Pos(px.toInt(), py.toInt()), Pos(vx.toInt(), vy.toInt()))
            }

    fun part1(input: List<String>) =
        parse(input)
            .asSequence()
            .map { it.step(100) }
            .groupBy {
                when {
                    it.x < W / 2 && it.y < H / 2 -> 0
                    it.x > W / 2 && it.y < H / 2 -> 1
                    it.x < W / 2 && it.y > H / 2 -> 2
                    it.x > W / 2 && it.y > H / 2 -> 3
                    else -> -1
                }
            }
            .filter { it.key != -1 }
            .map { it.value.size }
            .reduce { acc, i -> acc * i }

    fun part2(input: List<String>): Int {
        val bots = parse(input)

        val randomPicks = generateSequence { Random.nextInt(10000) }
            .take(1000)
            .map { entropy(bots go it) }
            .toList()

        val avg = randomPicks.average()
        val standardDeviation = sqrt(randomPicks.map { (it - avg).pow(2) }.average())

        val nStepsForTree = (1 until 10000).find {
            entropy(bots go it) < avg - 11 * standardDeviation
        }!!

        showTree(bots.go(nStepsForTree))
        return nStepsForTree
    }

    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}
