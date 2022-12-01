fun main() {

    fun getElves(input: List<String>): IntArray {
        val elfs = IntArray(input.count { it.isEmpty() } + 1) { 0 }
        var elfIndex = 0

        for (value in input) {
            if (value.isEmpty()) {
                elfIndex++
            } else {
                elfs[elfIndex] += value.toInt()
            }
        }

        return elfs
    }

    fun part1(input: List<String>): Int {
        return getElves(input).max()
    }

    fun part2(input: List<String>): Int {
        return getElves(input).sortedArrayDescending().take(3).sum()
    }

    println("Part 1: " + part1(readInput("Day01_test")))
    println("Part 1: " + part1(readInput("Day01")))

    println("Part 2: " + part2(readInput("Day01_test")))
    println("Part 2: " + part2(readInput("Day01")))
}
