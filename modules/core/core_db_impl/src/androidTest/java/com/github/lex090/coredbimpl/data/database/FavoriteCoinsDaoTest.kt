package com.github.lex090.coredbimpl.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.github.lex090.coredbapi.data.dao.FavoriteCoinsDao
import com.github.lex090.coredbapi.data.entity.FavoriteCoinEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class FavoriteCoinsDaoTest {
    private lateinit var favoriteCoinsDao: FavoriteCoinsDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        favoriteCoinsDao = db.favoriteCoinsDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun emptyGetterTest() = runTest {
        val expected = listOf<FavoriteCoinEntity>()
        val actual = favoriteCoinsDao.getFavoriteCoins()

        assertEquals(expected, actual)
    }

    @Test
    fun addFavoriteCoinsTest() = runTest {
        favoriteCoinsDao.addCoinToFavorites(testFavoriteCoinEntity1)
        val items = favoriteCoinsDao.getFavoriteCoins()

        assertEquals(1, items.size)
        assertEquals(testFavoriteCoinEntity1, items.first())
    }

    @Test
    fun removeFavoriteCoinsTest() = runTest {
        favoriteCoinsDao.addCoinToFavorites(testFavoriteCoinEntity1)
        favoriteCoinsDao.removeCoinFromFavorites("bitcoin")
        val items = favoriteCoinsDao.getFavoriteCoins()

        assertEquals(0, items.size)
    }

    @Test
    fun updateFavoriteCoinsTest() = runTest {
        favoriteCoinsDao.addCoinToFavorites(testFavoriteCoinEntity1)
        val newItem = testFavoriteCoinEntity1.copy(coinPrice = 20020.0)
        favoriteCoinsDao.updateFavoriteCoin(newItem)

        val items = favoriteCoinsDao.getFavoriteCoins()

        assertEquals(1, items.size)
        assertEquals(newItem, items.first())
    }

    @Test
    fun addFavoriteCoinsLikeListTest() = runTest {
        val listOfItems = listOf(
            testFavoriteCoinEntity1,
            testFavoriteCoinEntity2,
            testFavoriteCoinEntity3
        )
        favoriteCoinsDao.addCoinsToFavorites(listOfItems)

        val items = favoriteCoinsDao.getFavoriteCoins()

        assertEquals(listOfItems.size, items.size)
        assertEquals(listOfItems, items)
    }

    @Test
    fun clearFavoriteCoinsTest() = runTest {
        val listOfItems = listOf(
            testFavoriteCoinEntity1,
            testFavoriteCoinEntity2,
            testFavoriteCoinEntity3
        )
        favoriteCoinsDao.addCoinsToFavorites(listOfItems)
        favoriteCoinsDao.clearFavorites()

        val items = favoriteCoinsDao.getFavoriteCoins()

        assertEquals(0, items.size)
        assertEquals(listOf<FavoriteCoinEntity>(), items)
    }

    @Test
    fun subscribeUpdatingFavoriteCoinsTest() = runTest {
        val firstList = listOf<List<FavoriteCoinEntity>>()
        val secondList = listOf(testFavoriteCoinEntity1)
        val thirdList = listOf(testFavoriteCoinEntity1, testFavoriteCoinEntity2)
        val fourthList =
            listOf(
                testFavoriteCoinEntity1,
                testFavoriteCoinEntity2,
                testFavoriteCoinEntity3
            )

        favoriteCoinsDao
            .subscribeOnFavoriteCoinsDbUpdating()
            .test {
                assertEquals(firstList, awaitItem())
                favoriteCoinsDao.addCoinToFavorites(testFavoriteCoinEntity1)
                assertEquals(secondList, awaitItem())
                favoriteCoinsDao.addCoinToFavorites(testFavoriteCoinEntity2)
                assertEquals(thirdList, awaitItem())
                favoriteCoinsDao.addCoinToFavorites(testFavoriteCoinEntity3)
                assertEquals(fourthList, awaitItem())
            }
    }
}