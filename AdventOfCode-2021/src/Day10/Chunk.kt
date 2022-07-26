package Day10

data class Chunk(val opening: Char, val closing: Char, val errorPoint: Int, val autoCompletePoint: Int = 0)