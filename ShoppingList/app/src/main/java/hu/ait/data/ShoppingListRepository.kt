package hu.ait.data

import android.content.Context
import androidx.lifecycle.LiveData
import hu.ait.data.ShoppingItem
import hu.ait.data.ShoppingItemDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShoppingListRepository @Inject constructor(
    private val shoppingItemDao: ShoppingItemDao
) {

    fun getAllItems(): Flow<List<ShoppingItem>> {
        return shoppingItemDao.getAllItems()
    }


    suspend fun insertItem(item: ShoppingItem) {
        shoppingItemDao.insertItem(item)
    }

    suspend fun deleteItem(item: ShoppingItem) {
        shoppingItemDao.deleteItem(item)
    }

    suspend fun updateItem(item: ShoppingItem) {
        shoppingItemDao.updateItem(item)
    }

    suspend fun deleteAllItems() {
        shoppingItemDao.deleteAllItems()
    }

    fun getTotalPrice(): Flow<Float> {
        return shoppingItemDao.getTotalPrice()
    }

    fun getItemByIdFlow(id: Int): Flow<ShoppingItem> {
        return shoppingItemDao.getItemByIdFlow(id)
    }

    fun searchItems(name: String): Flow<List<ShoppingItem>> {
        return shoppingItemDao.searchItems(name)
    }

    fun filterItemsByPrice(minPrice: Float, maxPrice: Float): Flow<List<ShoppingItem>> {
        return shoppingItemDao.filterItemsByPrice(minPrice, maxPrice)
    }

    fun filterItemsByCategory(category: String): Flow<List<ShoppingItem>> {
        return shoppingItemDao.filterItemsByCategory(category)
    }
}