fun main() {

    // Test Case
    val testInput = readInput("Day08_test")
    val part1TestResult = Day08.part1(testInput)
    println(part1TestResult)
    check(part1TestResult == 21)

    val part2TestResult = Day08.part2(testInput)
    println(part2TestResult)
    check(part2TestResult == 8)

    // Actual Case
    val input = readInput("Day08")
    println("Part 1: " + Day08.part1(input))
    println("Part 2: " + Day08.part2(input))
}

private object Day08 {

    fun part1(lines: List<String>): Int {

        val treeHeightMap = TreeHeightMap(
            map = lines.map { line ->
                line.toCharArray().map { char ->
                    char.digitToInt()
                }
            }
        )

        treeHeightMap.print()

        return treeHeightMap.visibleCount
    }

    fun TreeHeightMap.isVisibleOn(x: Int, y: Int, direction: Direction): Boolean {
        val value = map[y][x]

        val xAxis: Boolean
        val range: IntProgression

        when (direction) {
            Direction.Top -> {
                range = IntRange(0, y)
                xAxis = false
            }

            Direction.Bottom -> {
                range = IntRange(y, yMax - 1).reversed()
                xAxis = false
            }

            Direction.Left -> {
                range = IntRange(0, x)
                xAxis = true
            }

            Direction.Right -> {
                range = IntRange(x, xMax - 1).reversed()
                xAxis = true
            }
        }

        for (i in range) {
            val checkValue = if (xAxis) map[y][i] else map[i][x]
            if (i == if (xAxis) x else y) {
                return true
            }
            if (checkValue >= value) {
                return false
            }
        }

        return false
    }

    fun part2(lines: List<String>): Int {

        val treeHeightMap = TreeHeightMap(
            map = lines.map { line ->
                line.toCharArray().map { char ->
                    char.digitToInt()
                }
            }
        )

        var max = 0

        treeHeightMap.iterateThroughMap { x, y ->
            val results = arrayOf(
                treeHeightMap.visibleCount(x, y, Direction.Top),
                treeHeightMap.visibleCount(x, y, Direction.Bottom),
                treeHeightMap.visibleCount(x, y, Direction.Left),
                treeHeightMap.visibleCount(x, y, Direction.Right)
            )
            val result = results[0] * results[1] * results[2] * results[3]
            if (result > max) {
                max = result
            }
        }

        return max
    }


    fun TreeHeightMap.visibleCount(x: Int, y: Int, direction: Direction): Int {
        val cord = x to y
        val value = map[y][x]

        val xAxis: Boolean
        val range: IntProgression

        when (direction) {
            Direction.Top -> {
                range = y downTo 0
                xAxis = false
            }

            Direction.Bottom -> {
                range = y until yMax
                xAxis = false
            }

            Direction.Left -> {
                range = x downTo 0
                xAxis = true
            }

            Direction.Right -> {
                range = x until xMax
                xAxis = true
            }
        }

        var index = 0

        for (i in range) {
            val currentCord = if (xAxis) i to y else x to i
            if (currentCord != cord) {
                val checkValue = map[currentCord.second][currentCord.first]
                index++
                if (checkValue >= value) {
                    return index
                }
            }
        }

        return index
    }

    enum class Direction {
        Top,
        Bottom,
        Left,
        Right
    }

    data class TreeHeightMap(
        val map: List<List<Int>>,
    ) {

        val xMax = map.first().size
        val yMax = map.size
        var visibleCount = 0

        init {
            calculateVisibleCount()
        }

        fun print() {
            println()
            map.forEach { chars ->
                chars.forEach { char ->
                    print(char)
                }
                println()
            }
            println()
        }

        private fun calculateVisibleCount() {
            // Add Edges
            visibleCount = (((yMax * 2) + (xMax * 2)) - 4)

            iterateThroughMap { x, y ->
                if (isVisible(x, y)) {
                    visibleCount++
                }
            }
        }

        fun iterateThroughMap(callback: (x: Int, y: Int) -> Unit) {
            for (y in 1 until yMax - 1) {
                for (x in 1 until xMax - 1) {
                    callback(x, y)
                }
            }
        }
    }

    fun TreeHeightMap.isVisible(x: Int, y: Int): Boolean {
        return Direction.values().firstOrNull { direction ->
            isVisibleOn(x, y, direction)
        } != null
    }
}