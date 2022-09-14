package com.github.lex090.coredbapi.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "favorites",
    indices = [Index(value = ["coin_id"], unique = true)]
)
data class FavoriteCoinEntity(
    @PrimaryKey
    @ColumnInfo(name = "coin_id")
    val coinId: String,
    @ColumnInfo(name = "coin_name")
    val coinName: String,
    @ColumnInfo(name = "coin_price")
    val coinPrice: Double,
    @ColumnInfo(name = "is_favorite_coin")
    val isFavoriteCoin: Boolean
)