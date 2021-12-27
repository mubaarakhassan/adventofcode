package Day08

class SevenSegmentDisplay(val input: Map<Int, Int>) {
    private val segments = mutableListOf<Segment>()

    init {
        input.forEach { (k, v) -> segments.add(Segment(k,v)) }
    }

    fun map(value: String){
        when (value.length) {
            2 -> segments[1].pattern = value
            3 -> segments[7].pattern = value
            4 -> segments[4].pattern = value
            7 -> segments[8].pattern = value
            5 -> {
                // 2 and 5 needs to have 1 same characters for 1 if not it is 3
                // 2 needs to have 2 same characters for 4 if not it is 5
                if(value.sumBy { if (segments[1].pattern.contains(it)) 1 else 0 } == 1){
                    if(value.sumBy { if (segments[4].pattern.contains(it)) 1 else 0 } == 2)
                        segments[2].pattern = value
                    else
                        segments[5].pattern = value
                }
                else
                    segments[3].pattern = value
            }
            6 -> {
                // 0 and 9 needs to have 2 same characters for 1 if not it is 6
                // 0 needs to have 3 same characters for 4 if not it is 9
                if(value.sumBy { if (segments[1].pattern.contains(it)) 1 else 0 } == 2){
                    if(value.sumBy { if (segments[4].pattern.contains(it)) 1 else 0 } == 3)
                        segments[0].pattern = value
                    else
                        segments[9].pattern = value
                }
                else
                    segments[6].pattern = value
            }
        }
    }

    fun search(value: String, checkPattern: Boolean = false){
        segments.forEach { it.compare(value, checkPattern) }
    }

    fun decode(value: String) : Int{
       return segments.first() { it.pattern == value }.digit
    }

    fun occurrences() : Int {
        return segments.sumOf { it.count }
    }
}

data class Segment (val digit: Int, val patternLength: Int, var pattern : String = "" , var count: Int = 0){
    fun compare(value: String, checkPattern: Boolean = false){ if(value.length == patternLength &&
        (if (checkPattern) value == pattern else true)) count++; }
}
