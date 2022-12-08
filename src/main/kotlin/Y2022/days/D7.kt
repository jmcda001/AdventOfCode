package main.kotlin.Y2022.days

import common.Day

class D7 : Day {
    private data class DirectoryTree(val root: Node) {
        val size: Int
            get() = root.size

        private fun Node.findAll(condition: (Node) -> Boolean): List<Node> {
            return contents.mapNotNull {
                it.findAll(condition) + if (condition(it)) { listOf(it) } else { emptyList() }
            }.flatten()
        }
        fun findAll(condition: (Node) -> Boolean): List<Node> {
            return root.findAll(condition)
        }
        fun printNested() {
            root.printNested(0)
        }
    }
    enum class NodeType { DIR, FILE }
    private data class Node(
        val parent: Node? = null,
        val name: String,
        val type: NodeType,
        val contents: MutableList<Node> = mutableListOf(),
        private var _size: Int? = null
    ) {
        val size: Int
            get() = if (_size != null) {
                _size!!
            } else {
                when (type) {
                    NodeType.DIR -> contents.sumOf { it.size }
                    NodeType.FILE -> _size ?: 0
                }.also {
                    _size = it
                }
            }
        fun printNested(depth: Int) {
            repeat(depth) { print("  ") }
            val t = when (type) {
                NodeType.DIR -> "d"
                NodeType.FILE -> "f"
            }
            println("$name $t $size")
            contents.forEach {
                it.printNested(depth + 1)
            }
        }
    }
    private fun buildDirectoryTree(input: List<String>): DirectoryTree {
        val root = Node(name = "/", type = NodeType.DIR)
        var currentNode = root
        input.drop(1).forEach { line ->
            if (line.startsWith("$")) {
                // Is command
                val (command, args) = line.split(" ").let {
                    Pair(it[1], it.getOrNull(2))
                }
                if (command == "cd") {
                    println("Find $args in ${currentNode.contents.joinToString { it.name }}")
                    if (args == "..") {
                        currentNode = currentNode.parent!!
                    } else {
                        currentNode = currentNode.contents.find { it.name == args }!! // Will NPE if missing
                    }
                }
            } else if (line.startsWith("dir")) {
                val dirName = line.split(" ")[1]
                currentNode.contents.add(Node(currentNode, dirName, NodeType.DIR))
            } else {
                val (filename, size) = line.split(" ").let { Pair(it[1], it[0].toInt()) }
                currentNode.contents.add(Node(currentNode, filename, NodeType.FILE, _size = size))
            }
        }
        return DirectoryTree(root = root)
    }
    override fun execute(input: List<String>, part: Int): String {
        val directoryTree = buildDirectoryTree(input)
        directoryTree.printNested()
        return if (part == 1) {
            val allUnder100k = directoryTree.findAll {
                it.type == NodeType.DIR && it.size <= 100_000
            }
            allUnder100k.sumOf { it.size }.toString()
        } else {
            val TotalSpace = 70_000_000
            val SpaceRequired = 30_000_000
            val remainingSpace = TotalSpace - directoryTree.size
            val spaceNeeded = SpaceRequired - remainingSpace
            val candidates = directoryTree.findAll {
                it.type == NodeType.DIR && it.size >= spaceNeeded
            }.sortedBy { it.size }
            candidates.first().size.toString()
        }
    }
}