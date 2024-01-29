package com.example.juegoscompose.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.juegoscompose.Utils.Constantes
import com.example.juegoscompose.data.model.JuegoEntity
import com.example.juegoscompose.data.repository.Converters




@Database(
    entities = [JuegoEntity::class],
    version = 3
)
@TypeConverters(Converters::class)
abstract class JuegoDataBase :RoomDatabase(){
    abstract fun getJuegoDao():JuegoDao

    companion object {
        @Volatile
        private var INSTANCE: JuegoDataBase? = null

        fun getDatabase(context: Context): JuegoDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JuegoDataBase::class.java,
                    Constantes.FICHA_DB
                )
                    .createFromAsset(Constantes.DATABASE_JUEGO_DB)
                    .fallbackToDestructiveMigrationFrom(4)
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}