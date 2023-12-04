import kotlin.math.pow

data class ScratchCard(
    val cardId: Int,
    val winningNums: List<Int>,
    val myNums: List<Int>
)

fun String.getNumbers() = this.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
fun main() {
    fun parseScratchCard(line: String): ScratchCard {
        //Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
        val scratchCardParts = line.split(":")
        val scratchCardNumberParts = scratchCardParts[1].split(" | ")

        return ScratchCard(
            cardId = scratchCardParts[0].removePrefix("Card ").trim().toInt(),
            winningNums = scratchCardNumberParts[0].getNumbers(),
            myNums = scratchCardNumberParts[1].getNumbers()
        )
    }

    fun calculateWinnings(nums: Set<Int>): Int {
        if (nums.isEmpty()) return 0

        if (nums.size == 1) return 1

        return 2.toDouble().pow(nums.size - 1).toInt()
    }

    fun part1(input: List<String>): Int {
        return input
            .map { parseScratchCard(it) }
            .map { it.winningNums.intersect(it.myNums.toSet()) }
            .map { calculateWinnings(it) }
            .sumOf { it }
    }


    fun initialiseMap(input: List<String>): MutableMap<Int, Int> {
        val map = mutableMapOf<Int, Int>()
        for (i in 1..input.size) {
            map[i] = 1
        }
        return map
    }

    fun part2(input: List<String>): Int {
        val scratchCardsWonMap = initialiseMap(input)
        input
            .map { parseScratchCard(it) }
            .map { scratchCard ->
                Pair(scratchCard, scratchCard.winningNums.intersect(scratchCard.myNums.toSet()))
            }
            .filter { (_, winningNums) -> winningNums.isNotEmpty() }
            .forEach { (scratchCard, winningNums) ->
                repeat(scratchCardsWonMap[scratchCard.cardId] ?: 1) {
                    for (i in 1 until winningNums.size + 1) {
                        val key = scratchCard.cardId + i
                        scratchCardsWonMap[key] = scratchCardsWonMap[key]!!.plus(1)
                    }
                }
            }

        return scratchCardsWonMap.map { entry ->
            entry.value
        }.sum()
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)

    val testInput2 = readInput("Day04_test")
    check(part2(testInput2) == 30)

    val input2 = readInput("Day04")
    part1(input2).println()
    part2(input2).println()
}
