package hu.ait.data

import androidx.room.*
import androidx.lifecycle.LiveData
import androidx.room.Query
import hu.ait.data.ShoppingItem
import kotlinx.coroutines.flow.Flow


@Dao
interface ShoppingItemDao {

    @Query("SELECT * FROM shopping_items")
    fun getAllItems(): Flow<List<ShoppingItem>>

    @Insert
    suspend fun insertItem(item: ShoppingItem)

    @Delete
    suspend fun deleteItem(item: ShoppingItem)

    @Update
    suspend fun updateItem(item: ShoppingItem)

    @Query("DELETE FROM shopping_items")
    suspend fun deleteAllItems()

    @Query("SELECT SUM(price) FROM shopping_items")
    fun getTotalPrice(): Flow<Float>

    @Query("SELECT * FROM shopping_items WHERE id = :id")
    fun getItemByIdFlow(id: Int): Flow<ShoppingItem>

    @Query("SELECT * FROM shopping_items WHERE name LIKE :name")
    fun searchItems(name: String): Flow<List<ShoppingItem>>

    @Query("SELECT * FROM shopping_items WHERE price BETWEEN :minPrice AND :maxPrice")
    fun filterItemsByPrice(minPrice: Float, maxPrice: Float): Flow<List<ShoppingItem>>

    @Query("SELECT * FROM shopping_items WHERE category = :category")
    fun filterItemsByCategory(category: String): Flow<List<ShoppingItem>>
}
