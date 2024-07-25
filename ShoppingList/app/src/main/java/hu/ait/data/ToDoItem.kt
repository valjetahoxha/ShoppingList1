package hu.ait.data


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_items")
data class ToDoItem(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "dueDate") var dueDate: String,
    @ColumnInfo(name = "done") var done: Boolean
) {
    constructor() : this(null, "", "", "", false)
}
