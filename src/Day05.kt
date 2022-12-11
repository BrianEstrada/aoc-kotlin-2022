fun main() {

    // Test Case
    val testInput = readInput("Day05_test")
    val part1TestResult = Day05.part1(testInput)
    println(part1TestResult)
    check(part1TestResult == "CMZ")

    val part2TestResult = Day05.part2(testInput)
    println(part2TestResult)
    check(part2TestResult == "MCD")

    // Actual Case
    val input = readInput("Day05")
    println("Part 1: " + Day05.part1(input))
    println("Part 2: " + Day05.part2(input))
}

private object Day05 {

    fun part1(lines: List<String>): String {
        val (stackMap, moveMap) = lines.splitLines()

        for (move in moveMap) {
            for (i in 0 until move.times) {
                val fromIndex = move.from - 1
                val toIndex = move.to - 1
                val indexOfFrom = stackMap[fromIndex].indexOfFirst { it.isNotEmpty() }
                val indexOfTo = stackMap[toIndex].indexOfLast { it.isEmpty() }
                val moveLetter = stackMap[fromIndex][indexOfFrom]
                stackMap[fromIndex][indexOfFrom] = ""
                if (indexOfTo == -1) {
                    stackMap[toIndex].add(0, moveLetter)
                } else {
                    stackMap[toIndex][indexOfTo] = moveLetter
                }
            }
        }

        return stackMap.joinToString("") { column ->
            column.first { letter ->
                letter.isNotEmpty()
            }
        }
    }

    fun part2(lines: List<String>): String {
        val (stackMap, moveMap) = lines.splitLines()

        stackMap.printMap()

        for (move in moveMap) {
            val fromIndex = move.from - 1
            val toIndex = move.to - 1
            val newList = stackMap[fromIndex].take(move.times)
            stackMap[fromIndex] = stackMap[fromIndex].subList(move.times, stackMap[fromIndex].size)
            stackMap[toIndex].addAll(0, newList)
            stackMap.printMap()
        }

        return stackMap.joinToString("") { column ->
            column.first { letter ->
                letter.isNotEmpty()
            }
        }
    }

    private fun List<List<String>>.printMap() {
        println()
        for (i in 0 until this.maxOf { it.size }) {
            for (j in 0 until this.size) {
                val value: String? = getOrNull(j)?.getOrNull(i)
                if (value.isNullOrBlank()) {
                    print("  ")
                } else {
                    print("$value ")
                }
            }
            println()
        }
        println()
    }

    private fun List<String>.splitLines(): Pair<MutableList<MutableList<String>>, List<Move>> {
        val stackMap = mutableListOf<String>()
        val moveMap = mutableListOf<String>()

        var ended = false

        for (line in this) {
            if (line.isBlank()) {
                ended = true
                continue
            }
            if (!ended) {
                stackMap.add(line)
            } else {
                moveMap.add(line)
            }
        }

        val columnMap = mutableListOf<MutableList<String>>()

        stackMap.forEach { stack ->
            stack.chunked(4).forEachIndexed { boxIndex, box ->
                val column = columnMap.getOrNull(boxIndex)
                if (column == null) columnMap.add(mutableListOf())
                val letter = box.replace("[", "")
                    .replace("]", "")
                    .trim()
                if (letter.isNotEmpty()) {
                    columnMap[boxIndex].add(letter)
                }
            }
        }

        return columnMap to Move.parse(moveMap)
    }

    data class Move(
        val times: Int,
        val from: Int,
        val to: Int,
    ) {
        companion object {

            fun parse(move: IntArray): Move {
                return Move(
                    move[0],
                    move[1],
                    move[2],
                )
            }

            fun parse(moves: List<String>): List<Move> {
                return moves.map { move ->
                    Move.parse(
                        move = move.split(" ")
                            .mapNotNull {
                                it.toIntOrNull()
                            }
                            .toIntArray()
                    )
                }
            }
        }
    }
}