package Day16

import readInput

fun main() {
    val input = readInput("Day16")
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    // Objective: Sum all version numbers in the packet hierarchy
    // Step 1: Convert hex to binary
    // Step 2: Parse packet structure recursively
    // Step 3: Sum all version numbers

    val binary = input[0].map { it.digitToInt(16).toString(2).padStart(4, '0') }.joinToString("")
    val (packet, _) = parsePacket(binary, 0)
    return sumVersions(packet)
}

fun part2(input: List<String>): Long {
    // Objective: Evaluate the expression represented by the packets
    // Step 1: Same parsing as part 1
    // Step 2: Evaluate based on packet type IDs

    val binary = input[0].map { it.digitToInt(16).toString(2).padStart(4, '0') }.joinToString("")
    val (packet, _) = parsePacket(binary, 0)
    return evaluatePacket(packet)
}

data class Packet(val version: Int, val typeId: Int, val value: Long?, val subPackets: List<Packet>)

fun parsePacket(binary: String, startIndex: Int): Pair<Packet, Int> {
    var index = startIndex
    val version = binary.substring(index, index + 3).toInt(2)
    index += 3
    val typeId = binary.substring(index, index + 3).toInt(2)
    index += 3

    if (typeId == 4) {
        // Literal value
        var valueStr = ""
        while (true) {
            val group = binary.substring(index, index + 5)
            index += 5
            valueStr += group.substring(1)
            if (group[0] == '0') break
        }
        return Packet(version, typeId, valueStr.toLong(2), emptyList()) to index
    } else {
        // Operator
        val lengthTypeId = binary[index]
        index++
        val subPackets = mutableListOf<Packet>()

        if (lengthTypeId == '0') {
            val subPacketsLength = binary.substring(index, index + 15).toInt(2)
            index += 15
            val endIndex = index + subPacketsLength
            while (index < endIndex) {
                val (subPacket, newIndex) = parsePacket(binary, index)
                subPackets.add(subPacket)
                index = newIndex
            }
        } else {
            val numSubPackets = binary.substring(index, index + 11).toInt(2)
            index += 11
            repeat(numSubPackets) {
                val (subPacket, newIndex) = parsePacket(binary, index)
                subPackets.add(subPacket)
                index = newIndex
            }
        }

        return Packet(version, typeId, null, subPackets) to index
    }
}

fun sumVersions(packet: Packet): Int {
    return packet.version + packet.subPackets.sumOf { sumVersions(it) }
}

fun evaluatePacket(packet: Packet): Long {
    return when (packet.typeId) {
        0 -> packet.subPackets.sumOf { evaluatePacket(it) }
        1 -> packet.subPackets.fold(1L) { acc, p -> acc * evaluatePacket(p) }
        2 -> packet.subPackets.minOf { evaluatePacket(it) }
        3 -> packet.subPackets.maxOf { evaluatePacket(it) }
        4 -> packet.value!!
        5 -> if (evaluatePacket(packet.subPackets[0]) > evaluatePacket(packet.subPackets[1])) 1 else 0
        6 -> if (evaluatePacket(packet.subPackets[0]) < evaluatePacket(packet.subPackets[1])) 1 else 0
        7 -> if (evaluatePacket(packet.subPackets[0]) == evaluatePacket(packet.subPackets[1])) 1 else 0
        else -> 0
    }
}