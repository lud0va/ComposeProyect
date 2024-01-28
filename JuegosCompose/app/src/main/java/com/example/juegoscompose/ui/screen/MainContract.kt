package com.example.juegoscompose.ui.screen

import com.example.juegoscompose.domain.model.Juego
import java.time.LocalDate

interface MainContract {
    sealed class Event(){
        class cambiarPrecio(val precio:Double):Event()
        class cambiarFecha(val  fecha:LocalDate):Event()
        class cambiarnombre(val nombre:String):Event()
        class addJuego(val juego: Juego):Event()
        class cargarJuego(val juego: Juego):Event()

        class deleteJuego(val juego: Juego):Event()
        class updateJuego(val juego: Juego):Event()
        object dameSiguiente:Event()
        object dameAnterior :Event()
        object cambiarEditorMode:Event()
    }
    data class State(
        val ultimo: Boolean = true,
        val primero: Boolean = true,
        val juego: Juego = Juego(1,"SI",2.2, LocalDate.now()),
        val error: String? = null,
        val editorOn:Boolean=false

    )
}