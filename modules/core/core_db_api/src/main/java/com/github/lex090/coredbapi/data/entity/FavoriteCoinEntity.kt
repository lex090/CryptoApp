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
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "coin_id")
    val coinId: String
)