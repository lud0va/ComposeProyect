package com.example.juegoscompose.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.juegoscompose.domain.model.Juego
import java.time.LocalDate

@Entity(tableName = "juego")
data class JuegoEntity(
    @PrimaryKey
    @ColumnInfo(name="id")
    val id: Int = 0,
    @ColumnInfo(name = "nombre")
    val nombre: String,
    @ColumnInfo(name = "precio")
    val precio: Double,
    @ColumnInfo(name = "fecha")
    val fecha: LocalDate,
    @ColumnInfo(name="genero")
    val genero:String,
    @ColumnInfo(name="dificultad")
    val dificultad:Int,
    @ColumnInfo(name="multijugador")
    val multijugador:Boolean
    ){

}

fun JuegoEntity.toJuego(): Juego = Juego(id, nombre, precio, fecha,genero, dificultad, multijugador)
fun Juego.toJuegoEntity(): JuegoEntity = JuegoEntity( id,nombre, precio, fecha,genero, dificultad, multijugador)