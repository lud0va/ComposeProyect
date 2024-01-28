package com.example.juegoscompose.domain.model

import java.time.LocalDate
import java.util.Date

data class  Juego(
    val id:Int,
    val nombre:String,
    val precio: Double,
    val fecha:LocalDate,


    )
