fun main() {

    // Test Case
    val testInput = readInput("Day02_test")
    val part1TestResult = Day02.part1(testInput)
    println(part1TestResult)
    check(part1TestResult == 15)

    val part2TestResult = Day02.part2(testInput)
    println(part2TestResult)
    check(part2TestResult == 12)

    // Actual Case
    val input = readInput("Day02")
    println("Part 1: " + Day02.part1(input))
    println("Part 2: " + Day02.part2(input))
}


private object Day02 {

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val (firstMove, secondMove) = line.split(" ")
            val shape1 = Shape.fromString(firstMove)
            val shape2 = Shape.fromString(secondMove)
            val result = fight(shape1, shape2)
            result.points + shape2.points
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val (firstMove, secondMove) = line.split(" ")
            val expectedResult = Result.fromString(secondMove)
            val shape2 = riggedFight(
                shape1 = Shape.fromString(firstMove),
                expectedResult = expectedResult
            )
            shape2.points + expectedResult.points
        }
    }

    private fun fight(shape1: Shape, shape2: Shape): Result {
        return when {
            shape1 == shape2 -> {
                Result.Tie
            }

            shape1.losesAgainst() == shape2 -> {
                Result.Win
            }

            else -> {
                Result.Loss
            }
        }
    }

    private fun riggedFight(shape1: Shape, expectedResult: Result): Shape {
        return when (expectedResult) {
            Result.Win -> shape1.losesAgainst()
            Result.Tie -> shape1
            Result.Loss -> shape1.winsAgainst()
        }
    }

    enum class Shape(
        val stringValues: Array<String>,
        val points: Int
    ) {
        Rock(
            stringValues = arrayOf("A", "X"),
            points = 1
        ),
        Paper(
            stringValues = arrayOf("B", "Y"),
            points = 2
        ),
        Scissor(
            stringValues = arrayOf("C", "Z"),
            points = 3
        );

        companion object {
            fun fromString(value: String): Shape {
                return values().first { shape ->
                    shape.stringValues.contains(value)
                }
            }
        }

        fun losesAgainst(): Shape {
            return when (this) {
                Rock -> Paper
                Paper -> Scissor
                Scissor -> Rock
            }
        }

        fun winsAgainst(): Shape {
            return when (this) {
                Rock -> Scissor
                Paper -> Rock
                Scissor -> Paper
            }
        }
    }

    enum class Result(
        val value: String,
        val points: Int
    ) {
        Win("Z", 6),
        Tie("Y", 3),
        Loss("X", 0);

        companion object {
            fun fromString(value: String): Result {
                return values().first { shape ->
                    shape.value == value
                }
            }
        }
    }
}