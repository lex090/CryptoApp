package com.github.lex090.coredbimpl.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.lex090.coredbapi.dao.FavoriteCoinsDao
import com.github.lex090.coredbapi.entity.FavoriteCoinEntity

const val DATABASE_NAME = "CryptoAppDatabase"

@Database(
    entities = [FavoriteCoinEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteCoinsDao(): FavoriteCoinsDao
}