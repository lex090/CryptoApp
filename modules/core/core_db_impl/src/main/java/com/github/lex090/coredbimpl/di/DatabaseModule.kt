package com.github.lex090.coredbimpl.di

import android.content.Context
import androidx.room.Room
import com.github.lex090.coredbapi.dao.FavoriteCoinDao
import com.github.lex090.coredbimpl.database.AppDatabase
import com.github.lex090.coredbimpl.database.DATABASE_NAME
import com.github.lex090.corediapi.ApplicationContext
import com.github.lex090.corediapi.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
object DatabaseModule {

    @ApplicationScope
    @Provides
    fun provideAppDataBase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    fun provideFavoriteCoinDao(appDatabase: AppDatabase): FavoriteCoinDao =
        appDatabase.favoriteCoinDao()
}
