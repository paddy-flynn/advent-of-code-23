fun main() {
    fun part1(input: List<String>): Int {
        val num = input.map { line ->
            line.filter { it.isDigit() }
        }.sumOf { numbersString ->
            "${numbersString.first()}${numbersString.last()}".println()
            "${numbersString.first()}${numbersString.last()}".toInt()
        }

        println("TOTAL: $num")
        return num
    }
val stringNumbersMap = mapOf(
    "zero" to "0",
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9",
)

    val startOfNumber = setOf(
        "z" ,
        "o" ,
        "t" ,
        "t" ,
        "f" ,
        "s" ,
        "e" ,
        "n"
    )

    val endOfNumber = setOf(
        "o" ,
        "e" ,
        "r" ,
        "x" ,
        "n" ,
        "t" ,
    )

    fun replaceText(line: String, mapKey: String): String {
        return line.replace(mapKey, stringNumbersMap[mapKey]!!)
    }

    fun noPossibleNumberOptionsStarts(firstNum: String): Boolean {
        return stringNumbersMap.keys.none { it.startsWith(firstNum) }
    }

    fun noPossibleNumberOptionsEnds(lastNum: String): Boolean {
        return stringNumbersMap.keys.none { it.endsWith(lastNum) }
    }

    fun convertLine(line: String): String {
        var newLine = line
        //find first number
        var firstNum = ""
        val charArray = line.toCharArray()
        for (i in charArray.indices){
            val c = charArray[i]
            if (c.isDigit()) {break}
            if(firstNum.isEmpty() && !startOfNumber.contains(c.toString())){
                continue
            }
            firstNum += c
            if(stringNumbersMap.containsKey(firstNum)){
                newLine = replaceText(line, firstNum)
                break
            }
            if(firstNum.isNotEmpty() && noPossibleNumberOptionsStarts(firstNum)){
                firstNum = firstNum.drop(1)
            }
        }

        //find last number
        var lastNum = ""
        val lastCharArray = newLine.toCharArray()

        for (i in lastCharArray.size downTo 0){
            val c = lastCharArray[i-1]
            if (c.isDigit()) {break}
            if(lastNum.isEmpty() && !endOfNumber.contains(c.toString())){
                continue
            }
            lastNum = "${c}${lastNum}"
            if(stringNumbersMap.containsKey(lastNum)){
                newLine = replaceText(newLine, lastNum)
                break
            }
            if(lastNum.isNotEmpty() && noPossibleNumberOptionsEnds(lastNum)){
                lastNum = lastNum.dropLast(1)
            }
        }

        //newLine.println()
        return newLine

    }

    fun replaceStringsWithNumbers(input: List<String>): List<String> {
        return input.map { convertLine(it) }
    }

    fun part2(input: List<String>): Int {
        val data = replaceStringsWithNumbers(input)
        return part1(data)
    }




    // test if implementation meets criteria from the description, like:
    //val testInput = readInput("Day01_test")
    //check(part1(testInput) == 142)

    val testInput2 = readInput("Day01_test2")
    check(part2(testInput2) == 281)

    val input2 = readInput("Day01")
    //part1(input2).println()
    part2(input2).println()
}
