package hu.ait.data.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.ait.data.ShoppingDatabase
import hu.ait.data.ShoppingItem
import hu.ait.data.ShoppingItemDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideShoppingDatabase(@ApplicationContext context: Context): ShoppingDatabase {
        return Room.databaseBuilder(
            context,
            ShoppingDatabase::class.java,
            "shopping.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideShoppingItemDao(shoppingDatabase: ShoppingDatabase): ShoppingItemDao {
        return shoppingDatabase.shoppingItemDao()
    }
}
