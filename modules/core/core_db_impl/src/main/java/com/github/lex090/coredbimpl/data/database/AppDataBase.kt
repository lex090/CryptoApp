package com.github.lex090.coredbimpl.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.lex090.coredbapi.data.dao.FavoriteCoinsDao
import com.github.lex090.coredbapi.data.entity.FavoriteCoinEntity

const val DATABASE_NAME = "CryptoAppDatabase"

@Database(
    entities = [FavoriteCoinEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteCoinsDao(): FavoriteCoinsDao
}
