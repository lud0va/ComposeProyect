package com.example.juegoscompose.ui.screen

import com.example.juegoscompose.Utils.Constantes
import com.example.juegoscompose.domain.model.Juego
import java.time.LocalDate




interface MainContract {
    sealed class Event(){

        class cambiarMultiplayer(val mult:Boolean):Event()
        class cambiarGenero(val string:String):Event()
        class cambiarDiff(val diff:Int):Event()
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
        object cambiarExtMode:Event()
    }
    data class State(
        val expanded:Boolean=false,
        val ultimo: Boolean = true,
        val primero: Boolean = true,
        val juego: Juego = Juego(1, Constantes.S,2.2, LocalDate.now(),Constantes.ACCION,2,true),
        val error: String? = null,
        val editorOn:Boolean=false

    )
}