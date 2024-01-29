package com.example.juegoscompose.domain.model

import java.time.LocalDate


data class  Juego(
    val id:Int,
    val nombre:String,
    val precio: Double,
    val fecha:LocalDate,
    val genero:String,
    val dificultad:Int,
    val multijugador:Boolean


    )
