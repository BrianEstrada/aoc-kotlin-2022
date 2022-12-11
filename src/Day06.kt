fun main() {

    // Test Case
    val testInput = readInput("Day06_test")
    println("Part 1 Test")
    Day06.part1(testInput)
    println("Part 2 Test")
    Day06.part2(testInput)

    // Actual Case
    val input = readInput("Day06")
    println("Part 1")
    Day06.part1(input)
    println("Part 2")
    Day06.part2(input)
}

private object Day06 {

    fun part1(lines: List<String>): Int {

        lines.forEachIndexed { index, line ->
            val answer = line.toCharArray().findAnswer(4)
            println("$index = $answer")
        }

        return 0
    }

    fun part2(lines: List<String>): Int {

        lines.forEachIndexed { index, line ->
            val answer = line.toCharArray().findAnswer(14)
            println("$index = $answer")
        }

        return 0
    }

    private fun CharArray.findAnswer(size: Int): Int {
        val chars = arrayOfNulls<Char>(size)
        forEachIndexed { index: Int, char: Char ->
            chars[index % size] = char
            if (index > (size - 1) && !chars.hasDuplicates(size)) {
                return index + 1
            }
        }
        return 0
    }

    fun Array<Char?>.hasDuplicates(size: Int): Boolean {
        return this.distinct().count() != size
    }
}