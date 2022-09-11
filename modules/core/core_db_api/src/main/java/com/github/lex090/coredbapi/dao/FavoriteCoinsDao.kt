package com.github.lex090.coredbapi.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.lex090.coredbapi.entity.FavoriteCoinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCoinsDao {

    @Query("SELECT * FROM favorites")
    suspend fun getFavoriteCoins(): List<FavoriteCoinEntity>

    @Query("SELECT * FROM favorites")
    fun subscribeOnFavoriteCoinsDbUpdating(): Flow<List<FavoriteCoinEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCoinToFavorites(favoriteCoin: FavoriteCoinEntity)

    @Query("DELETE FROM favorites WHERE coin_id = :coinId")
    suspend fun removeCoinFromFavorites(coinId: String)

    @Query("DELETE FROM favorites")
    suspend fun clearFavorites()
}