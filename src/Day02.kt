data class CubeGame(
    val id: Int,
    var blueCount: Int,
    var redCount: Int,
    var greenCount: Int,
    var isValidGame: Boolean = true
)

const val BLUE_CUBE_AMOUNT = 14
const val GREEN_CUBE_AMOUNT = 13
const val RED_CUBE_AMOUNT = 12

fun String.gameId(): Int {
    return this.filter { it.isDigit() }.toInt()
}

fun main() {
    fun parseGameStr(gameStr: String): CubeGame {
        //Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        //Get Game Details
        val gameDetails = gameStr.split(":")
        val gameId = gameDetails[0].gameId()

        //Get Game Sets
        val gameSets = gameDetails[1].split(";")

        //Initialise cube game
        val cubeGame = CubeGame(gameId, blueCount = 0, redCount = 0, greenCount = 0, isValidGame = true)

        gameSets.forEach { gameSet ->
            //Get the cube counts per set, e.g ["12 blue" , "3 red"]
            val cubeCounts = gameSet.split(",")
            cubeCounts.forEach { cubeCount ->
                //separate number and colour and filter out empty strings
                val cubeDetails = cubeCount.split(" ").filter { it.isNotEmpty() }
                val (cubeCount, cubeColour) = cubeDetails
                when (cubeColour) {
                    "blue" -> {
                        if (cubeCount.toInt() > BLUE_CUBE_AMOUNT) cubeGame.isValidGame = false
                        cubeGame.blueCount = cubeGame.blueCount.coerceAtLeast(cubeCount.toInt())
                    }

                    "red" -> {
                        if (cubeCount.toInt() > RED_CUBE_AMOUNT) cubeGame.isValidGame = false
                        cubeGame.redCount = cubeGame.redCount.coerceAtLeast(cubeCount.toInt())
                    }

                    "green" -> {
                        if (cubeCount.toInt() > GREEN_CUBE_AMOUNT) cubeGame.isValidGame = false
                        cubeGame.greenCount = cubeGame.greenCount.coerceAtLeast(cubeCount.toInt())
                    }
                }
            }
        }
        return cubeGame
    }

    fun part1(input: List<String>): Int {
        return input
            .map { gameStr -> parseGameStr(gameStr) }
            .filter { it.isValidGame }
            .sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { gameStr -> parseGameStr(gameStr) }
            .sumOf { it.blueCount * it.redCount * it.greenCount }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)

    val testInput2 = readInput("Day02_test")
    check(part2(testInput2) == 2286)

    val input2 = readInput("Day02")
    part1(input2).println()
    part2(input2).println()
}
