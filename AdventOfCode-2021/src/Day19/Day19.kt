package Day19

import readInput
import kotlin.math.*

fun main() {
    val input = readInput("Day19")
    val result = solve(input)
    println(result.first)
    println(result.second)
}

fun solve(input: List<String>): kotlin.Pair<Int, Int> {
    // Objective: Find all beacons and scanner positions through rotation/translation
    // Step 1: Parse scanner data
    // Step 2: Find overlapping scanners by trying all rotations
    // Step 3: Merge scanner data and track positions
    // Step 4: Count unique beacons and max Manhattan distance

    val scanners = parseScanners(input)
    val (beacons, scannerPositions) = findAllBeacons(scanners)

    val part1 = beacons.size
    val part2 = scannerPositions.maxOf { s1 ->
        scannerPositions.maxOf { s2 -> manhattanDistance(s1, s2) }
    }

    return part1 to part2
}

data class Point3D(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Point3D) = Point3D(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Point3D) = Point3D(x - other.x, y - other.y, z - other.z)
}

fun parseScanners(input: List<String>): List<List<Point3D>> {
    return input.fold(mutableListOf<MutableList<Point3D>>()) { scanners, line ->
        when {
            line.startsWith("---") -> scanners.apply { add(mutableListOf()) }
            line.isNotBlank() -> {
                val (x, y, z) = line.split(",").map { it.toInt() }
                scanners.last().add(Point3D(x, y, z))
                scanners
            }
            else -> scanners
        }
    }
}

fun findAllBeacons(scanners: List<List<Point3D>>): kotlin.Pair<Set<Point3D>, List<Point3D>> {
    val knownBeacons = scanners[0].toMutableSet()
    val processed = mutableSetOf(0)
    val scannerPositions = mutableListOf(Point3D(0, 0, 0))

    while (processed.size < scanners.size) {
        for (i in scanners.indices) {
            if (i in processed) continue

            val overlap = findOverlap(knownBeacons, scanners[i])
            if (overlap != null) {
                val (rotatedBeacons, scannerPos) = overlap
                knownBeacons.addAll(rotatedBeacons)
                scannerPositions.add(scannerPos)
                processed.add(i)
                break
            }
        }
    }

    return knownBeacons to scannerPositions
}

fun findOverlap(known: Set<Point3D>, scanner: List<Point3D>): kotlin.Pair<Set<Point3D>, Point3D>? {
    for (rotation in allRotations()) {
        val rotated = scanner.map { rotate(it, rotation) }

        for (knownBeacon in known) {
            for (rotatedBeacon in rotated) {
                val offset = knownBeacon - rotatedBeacon
                val translated = rotated.map { it + offset }

                if (translated.count { it in known } >= 12) {
                    return translated.toSet() to offset
                }
            }
        }
    }
    return null
}

fun allRotations(): List<List<Int>> {
    // Generate all 24 possible 3D rotations
    val rotations = mutableListOf<List<Int>>()
    for (face in 0..5) {
        for (turn in 0..3) {
            rotations.add(listOf(face, turn))
        }
    }
    return rotations
}

fun rotate(point: Point3D, rotation: List<Int>): Point3D {
    val (face, turn) = rotation
    var (x, y, z) = listOf(point.x, point.y, point.z)

    // Face the correct direction
    when (face) {
        0 -> {} // +x forward
        1 -> { val tmp = x; x = -y; y = tmp } // +y forward
        2 -> { x = -x; y = -y } // -x forward
        3 -> { val tmp = x; x = y; y = -tmp } // -y forward
        4 -> { val tmp = x; x = z; z = -tmp } // +z forward
        5 -> { val tmp = x; x = -z; z = tmp } // -z forward
    }

    // Turn around the forward axis
    repeat(turn) {
        val tmp = y
        y = -z
        z = tmp
    }

    return Point3D(x, y, z)
}

fun manhattanDistance(p1: Point3D, p2: Point3D): Int {
    return abs(p1.x - p2.x) + abs(p1.y - p2.y) + abs(p1.z - p2.z)
}