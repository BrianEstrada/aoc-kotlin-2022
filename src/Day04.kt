fun main() {

    // Test Case
    val testInput = readInput("Day04_test")
    val part1TestResult = Day04.part1(testInput)
    println(part1TestResult)
    check(part1TestResult == 2)

    val part2TestResult = Day04.part2(testInput)
    println(part2TestResult)
    check(part2TestResult == 4)

    // Actual Case
    val input = readInput("Day04")
    println("Part 1: " + Day04.part1(input))
    println("Part 2: " + Day04.part2(input))
}

private object Day04 {

    fun part1(input: List<String>): Int {
        var total = 0

        for (line in input) {
            val (first, second) = line.split(',')
            val firstSet = first.toNumberSet()
            val secondSet = second.toNumberSet()
            when {
                (firstSet intersect secondSet) == firstSet -> total++
                (firstSet intersect secondSet) == secondSet -> total++
                (secondSet intersect firstSet) == firstSet -> total++
                (secondSet intersect firstSet) == secondSet -> total++
            }
        }

        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0

        for (line in input) {
            val (first, second) = line.split(',')
            val firstSet = first.toNumberSet()
            val secondSet = second.toNumberSet()
            val intersecting = (firstSet intersect secondSet)
            if (intersecting.isNotEmpty()) {
                total++
            }
        }

        return total
    }

    private fun String.toNumberSet(): Set<Int> {
        val (start, end) = this.split("-")
        return (start.toInt()..end.toInt()).toSet()
    }
}