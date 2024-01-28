package com.example.juegoscompose.data.di

import android.content.Context
import androidx.room.Room
import com.example.juegoscompose.data.JuegoDao
import com.example.juegoscompose.data.JuegoDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): JuegoDataBase {
        return Room.databaseBuilder(
            appContext,
            JuegoDataBase::class.java,
            "app.db"
        ).fallbackToDestructiveMigration()
            .build()
    }
    @Singleton
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO


    @Provides
    @Singleton
    fun provideFichaMascotaDao(database: JuegoDataBase) :JuegoDao{
        return database.getJuegoDao()
    }

}