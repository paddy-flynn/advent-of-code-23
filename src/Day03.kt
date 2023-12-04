fun main() {

    fun Char.isDot() : Boolean = this== '.'
    fun Char.isSymbol() : Boolean = !this.isLetterOrDigit() && !this.isDot()


    fun parseIntoMatrix(input: List<String>): List<CharArray> {
        return input.map {
            it.toCharArray()
        }
    }

    fun isSymbol(char: Char): Boolean {
        return !char.isLetterOrDigit()
    }

    fun isCharTouchingSymbol(matrix: List<CharArray>, index: Int, charArrayIndex: Int): Boolean {
        val UP_BOUNDRY = 0
        val RIGHT_BOUNDRY = matrix[0].size-1
        val DOWN_BOUNDRY = matrix.size-1
        val LEFT_BOUNDRY = 0

        //Check Up-Left
        if(index - 1 >= UP_BOUNDRY && charArrayIndex -1 >= LEFT_BOUNDRY){
            val char = matrix[index-1][charArrayIndex-1]
            if(char.isSymbol()){
                return true
            }
        }
        //Check Up
        if(index - 1 >= UP_BOUNDRY){
            val char = matrix[index-1][charArrayIndex]
            if(char.isSymbol()){
                return true
            }
        }
        //Check Up-Right
        if(index - 1 >= UP_BOUNDRY && charArrayIndex +1 <= RIGHT_BOUNDRY){
            val char = matrix[index-1][charArrayIndex+1]
            if(char.isSymbol()){
                return true
            }
        }
        //Check Right
        if(charArrayIndex +1 <= RIGHT_BOUNDRY){
            val char = matrix[index][charArrayIndex+1]
            if(char.isSymbol()){
                return true
            }
        }
        //Check Down-Right
        if(index + 1 <= DOWN_BOUNDRY && charArrayIndex +1 <= RIGHT_BOUNDRY){
            val char = matrix[index+1][charArrayIndex+1]
            if(char.isSymbol()){
                return true
            }
        }
        //Check Down
        if(index + 1 <= DOWN_BOUNDRY ){
            val char = matrix[index+1][charArrayIndex]
            if(char.isSymbol()){
                return true
            }
        }
        //Check Down-Left
        if(index + 1 <= DOWN_BOUNDRY && charArrayIndex -1 >= LEFT_BOUNDRY){
            val char = matrix[index+1][charArrayIndex-1]
            if(char.isSymbol()){
                return true
            }
        }
        //Check Left
        if(charArrayIndex -1 >= LEFT_BOUNDRY){
            val char = matrix[index][charArrayIndex-1]
            if(char.isSymbol()){
                return true
            }
        }
        return false
    }

    fun part1(input: List<String>): Int {
        //Read input into 2d array
        val matrix = parseIntoMatrix(input)
        var sumTotal = 0
        var hasTouchedSymbol = false
        var numStr = ""
        //read single character at a time
        matrix.forEachIndexed { index, charArray ->
            charArray.forEachIndexed { charArrayIndex, char ->
                //check if number or "."
                if(char.isDigit()){
                    numStr += char
                    //check if the number is touching a symbol
                    if(isCharTouchingSymbol(matrix ,index, charArrayIndex)){
                        hasTouchedSymbol = true
                    }else{
                        //can we get another char or are we out of bounds?
                        if(charArrayIndex+1 > charArray.size){
                            //add num string to total
                            if(numStr.isNotEmpty() && hasTouchedSymbol) {
                                sumTotal += numStr.toInt()
                                numStr = ""
                                hasTouchedSymbol = false
                            }
                        }
                    }
                }else{
                    if(numStr.isNotEmpty() && hasTouchedSymbol) {
                        sumTotal += numStr.toInt()
                    }
                    numStr = ""
                    hasTouchedSymbol = false
                }
            }
            if(numStr.isNotEmpty() && hasTouchedSymbol) {
                sumTotal += numStr.toInt()
            }
            numStr = ""
            hasTouchedSymbol = false
        }
        return sumTotal
    }


    fun part2(input: List<String>): Int {
        val matrix = parseIntoMatrix(input)
        val foundNumbers = mutableMapOf<Set<Pair<Int, Int>>, Int>()
        val digits = mutableListOf<Char>()
        val positions = mutableListOf<Pair<Int, Int>>()
        matrix.forEachIndexed { index, charArray ->
            charArray.forEachIndexed { charArrayIndex, char ->
                if (char.isDigit()) {
                    digits.add(char)
                    positions.add(Pair(charArrayIndex, index))
                }
                if (!char.isDigit() || charArray.size-1 == charArrayIndex) {
                    if (positions.isNotEmpty()) {
                        foundNumbers[positions.toSet()] = digits.joinToString("").toInt()
                    }

                    digits.clear()
                    positions.clear()
                }
            }
        }
        val gearRatios = mutableListOf<Pair<Int, Int>>()

        matrix.forEachIndexed { index, charArray ->
            charArray.forEachIndexed { charArrayIndex, char ->
                if (char == '*') {
                    val adjacent = matrix.adjacentToCoords(Pair(charArrayIndex, index))
                    val numbersAdjacentToIt = foundNumbers.filter { (positions, _) ->
                        positions.intersect(adjacent).isNotEmpty()
                    }.values.toList()
                    if (numbersAdjacentToIt.size == 2) {
                        val (first, second) = numbersAdjacentToIt
                        gearRatios.add(Pair(first, second))
                    }
                }
            }
        }

        return gearRatios.sumOf { it.first * it.second }
    }




    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
 //   check(part1(testInput) == 4361)

    val testInput2 = readInput("Day03_test")
    check(part2(testInput2) == 467835)

    val input2 = readInput("Day03")
    //part1(input2).println()
  part2(input2).println()
}

private fun List<CharArray>.adjacentToCoords(position: Pair<Int, Int>): Set<Pair<Int, Int>> {
    val (x, y) = position
    val validPositions = mutableListOf<Pair<Int, Int>>()
    for (dy in -1..1) {
        for (dx in -1..1) {
            if (dx == 0 && dy == 0) continue
            val newX = x + dx
            val newY = y + dy
            if (newX < 0 || newY < 0) continue
            if (newY >= size || newX >= this[newY].size) continue
            validPositions.add(Pair(newX, newY))
        }
    }
    return validPositions.toSet()
}
