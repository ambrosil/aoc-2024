import utils.println
import utils.readInput

fun main() {

    fun MutableSet<String>.allPairs() =
        flatMapIndexed { index, s1 ->
            mapIndexedNotNull { index2, s2 ->
                if (index2 > index) s1 to s2 else null
            }
        }

    // algoritmo di Bron-Kerbosch per trovare il massimo clique in un grafo
    fun MutableMap<String, MutableSet<String>>.bronKerbosch(
        p: Set<String>,
        r: Set<String> = emptySet(),
        x: Set<String> = emptySet()
    ): Set<String> =
        if (p.isEmpty() && x.isEmpty()) r
        else {
            val withMostNeighbors: String = (p + x).maxBy { this.getValue(it).size }
            p.minus(this.getValue(withMostNeighbors)).map { v ->
                bronKerbosch(
                    p intersect this.getValue(v),
                    r + v,
                    x intersect this.getValue(v)
                )
            }.maxBy { it.size }
        }

    fun parse(input: List<String>): MutableMap<String, MutableSet<String>> =
        input
            .map { it.split("-") }.map { it[0] to it[1] }
            .fold(mutableMapOf()) { acc, v ->
                acc.getOrPut(v.first) { mutableSetOf(v.second) } += v.second
                acc.getOrPut(v.second) { mutableSetOf(v.first) } += v.first
                acc
            }

    fun part1(input: List<String>): Int {
        val groups = parse(input)
        val cliques = mutableSetOf<Set<String>>()

        groups.forEach { (k, v) ->
            val clique = v.allPairs().filter { (b, c) -> b in groups.getValue(c) }
            cliques += clique.map { listOf(it.first, it.second, k).toSet() }
        }

        return cliques.count { it.any { s -> s.startsWith("t") } }
    }

    fun part2(input: List<String>): String {
        val groups = parse(input)
        return groups.bronKerbosch(groups.keys).sorted().joinToString(",")
    }

    val input = readInput("Day23")
    part1(input).println()
    part2(input).println()
}
