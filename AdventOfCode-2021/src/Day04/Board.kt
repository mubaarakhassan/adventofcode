package Day04

data class Board(val id: Int, val rows: List<Row> =listOf()){
    fun validateWinningCondition() : Boolean{
        // Check if board has won; by checking first if all rows are marked.
        if(rows.firstOrNull { it -> it.validateWinningCondition() } != null)
            return true

        // If not then check if rows are all marked.
        loop@ for (index in rows.indices) {
            if (rows.filter { it.rowItems[index].marked; }.size == rows.size)
                return true
        }

        return false
    }

    override fun equals(obj: Any?): Boolean {
        return obj?.let { id == (it as Board).id } ?: false
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

data class Row(val rowItems: List<RowItem> = listOf()){
    fun validateWinningCondition() : Boolean {
        return  rowItems.filter { it.marked }.size == rowItems.size
    }

    fun getTotalUnmarkedSum(): Int {
        return rowItems.filter { !it.marked }.sumOf { it.num }
    }
}

data class RowItem(var num: Int, var marked: Boolean)