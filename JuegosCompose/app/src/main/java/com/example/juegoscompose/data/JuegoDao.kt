package com.example.juegoscompose.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.juegoscompose.data.model.JuegoEntity


@Dao
interface JuegoDao {
    @Query("SELECT * FROM juego")
    suspend fun getAll():List<JuegoEntity>
    @Query("SELECT * FROM juego WHERE id=:id")
    suspend fun getJuego(id: Int): JuegoEntity

    @Insert
    suspend fun add(juegoEntity: JuegoEntity)
    @Update
    suspend fun update(juegoEntity: JuegoEntity)

    @Delete
    suspend fun delete(juegoEntity: JuegoEntity)
    @Query("SELECT * FROM juego ORDER BY id DESC LIMIT 1")
    suspend fun getLastJuego(): JuegoEntity

    @Query("SELECT * FROM juego LIMIT 1")
    suspend fun getFirstJuego(): JuegoEntity

}