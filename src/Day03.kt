fun main() {

    // Test Case
    val testInput = readInput("Day03_test")
    val part1TestResult = Day03.part1(testInput)
    println(part1TestResult)
    check(part1TestResult == 157)

    val part2TestResult = Day03.part2(testInput)
    println(part2TestResult)
    check(part2TestResult == 70)

    // Actual Case
    val input = readInput("Day03")
    println("Part 1: " + Day03.part1(input))
    println("Part 1: " + Day03.part2(input))
}

private object Day03 {
    val charPoints = buildMap {
        for (i in 'a'..'z') {
            this[i] = i.toInt() - 96
        }
        for (i in 'A'..'Z') {
            this[i] = i.toInt() - 38
        }
    }

    fun part1(input: List<String>): Int {
        var total = 0
        for (line in input) {
            val (first, second) = line.chunked(line.length / 2)
            val secondChars = second.toCharArray()
            total += first.toCharArray()
                .filter { char ->
                    secondChars.contains(char)
                }
                .distinct()
                .mapNotNull { char ->
                    charPoints[char]
                }
                .sum()
        }
        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0
        val lineGroups = input.chunked(3)

        for (lines in lineGroups) {
            val firstLine = lines[0].toCharArray().distinct()
            val secondLine = lines[1].toCharArray().distinct()
            total += lines[2].toCharArray()
                .distinct()
                .filter { firstLine.contains(it) && secondLine.contains(it) }
                .mapNotNull { char ->
                    charPoints[char]
                }
                .sum()
        }

        return total
    }
}