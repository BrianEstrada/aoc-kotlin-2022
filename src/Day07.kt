fun main() {

    // Test Case
    val testInput = readInput("Day07_test")
    val part1TestResult = Day07.part1(testInput)
    println(part1TestResult)
    check(part1TestResult == 95437)

    val part2TestResult = Day07.part2(testInput)
    println(part2TestResult)
    check(part2TestResult.second == 24933642)

    // Actual Case
    val input = readInput("Day07")
    println("Part 1: " + Day07.part1(input))
    println("Part 2: " + Day07.part2(input))
}

private object Day07 {

    fun part1(lines: List<String>): Int {
        return buildDirectories(lines).getDirectoriesBySize { fileSize -> fileSize < 100_000 }.sumOf { it.second }
    }

    fun part2(lines: List<String>): Pair<String, Int> {
        val maxSpace = 70_000_000
        val requiredSpace = 30_000_000
        val rootDirectory = buildDirectories(lines)
        val directories = rootDirectory.getDirectoriesBySize { fileSize ->
            fileSize >= rootDirectory.fileSize() - (maxSpace - requiredSpace)
        }
        return directories.minBy { it.second }
    }

    private fun buildDirectories(lines: List<String>): Directory {
        val rootDirectory = Directory("/")
        var currentDirectory: Directory? = null
        var lastCommand: Command.Type? = null

        for (line in lines) {
            val command = Command(line.split(" "))

            if (command.isCommand) {
                lastCommand = command.getType()
                if (lastCommand != Command.Type.ChangeDirectory) continue
                currentDirectory = when (command.thirdValue) {
                    "/" -> rootDirectory
                    ".." -> currentDirectory?.parentDirectory
                    else -> currentDirectory?.directories?.first { directory ->
                        directory.name == command.thirdValue
                    }
                }
            } else {
                if (lastCommand == Command.Type.List) {
                    if (command.firstValue == "dir") {
                        currentDirectory?.directories?.add(
                            Directory(command.secondValue).apply {
                                parentDirectory = currentDirectory
                            }
                        )
                    } else {
                        currentDirectory?.files?.set(
                            command.secondValue,
                            command.firstValue.toInt()
                        )
                    }
                }
            }
        }

        return rootDirectory
    }

    data class Command(
        val firstValue: String,
        val secondValue: String,
        val thirdValue: String?,

        ) {

        constructor(values: List<String>) : this(
            firstValue = values[0],
            secondValue = values[1],
            thirdValue = values.getOrNull(2)
        )

        val isCommand: Boolean = (firstValue == "$")

        fun getType(): Type {
            return Type.parse(secondValue)
        }

        enum class Type(val value: String) {
            ChangeDirectory("cd"),
            List("ls");

            companion object {
                fun parse(value: String): Type {
                    return values().first { it.value == value }
                }
            }
        }
    }

    data class Directory(
        val name: String,
        val files: MutableMap<String, Int> = mutableMapOf(),
        val directories: MutableSet<Directory> = mutableSetOf(),
    ) {

        var parentDirectory: Directory? = null

        fun getDirectoriesBySize(op: (fileSize: Int) -> Boolean): List<Pair<String, Int>> {
            return buildList {
                val fileSize = fileSize()
                if (op(fileSize)) {
                    add(name to fileSize)
                }
                addAll(
                    directories.map { it.getDirectoriesBySize(op) }.flatten()
                )
            }
        }

        fun fileSize(): Int {
            return files.map { it.value }.sum() + directories.sumOf { it.fileSize() }
        }
    }
}