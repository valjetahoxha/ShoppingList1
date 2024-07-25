package hu.ait.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name: String,
    val description: String,
    val price: Float,
    val category: String,
    var bought: Boolean
) {


    fun getPriceString() = "$$price"

}
