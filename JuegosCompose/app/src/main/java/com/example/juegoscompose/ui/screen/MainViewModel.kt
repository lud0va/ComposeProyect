package com.example.juegoscompose.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juegoscompose.Utils.Constantes
import com.example.juegoscompose.data.model.toJuego
import com.example.juegoscompose.domain.model.Juego
import com.example.juegoscompose.domain.usecases.AddJuegoUseCase
import com.example.juegoscompose.domain.usecases.DeleteJuegoUseCase
import com.example.juegoscompose.domain.usecases.GetAllJuegosUseCase
import com.example.juegoscompose.domain.usecases.GetFirstJuegoUseCase
import com.example.juegoscompose.domain.usecases.GetJuegoUseCase
import com.example.juegoscompose.domain.usecases.GetLastJuegoUseCase
import com.example.juegoscompose.domain.usecases.UpdateJuegoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

import javax.inject.Inject



@HiltViewModel
class MainViewModel @Inject constructor(
    private val addJuegoUseCase: AddJuegoUseCase,
    private val getJuegoUseCase: GetJuegoUseCase,
    private val deleteJuegoUseCase: DeleteJuegoUseCase,
    private val updateJuegoUseCase: UpdateJuegoUseCase,
    private val getAllJuegosUseCase: GetAllJuegosUseCase,
    private val getFirstJuegoUseCase: GetFirstJuegoUseCase,
    private val getLastJuegoUseCase: GetLastJuegoUseCase


) : ViewModel() {
    private val _uiState: MutableStateFlow<MainContract.State> by lazy {
        MutableStateFlow(MainContract.State())
    }
    val uiState: StateFlow<MainContract.State> = _uiState
    private val _uiError = Channel<String>()
    val uiError = _uiError.receiveAsFlow()
    private val _editorOn = MutableStateFlow(uiState.value.editorOn)
    val editorOn: StateFlow<Boolean> = _editorOn.asStateFlow()
    private var pos = 1

    init {
        viewModelScope.launch {

            try {
                cargarJuego(getJuegoUseCase.invoke(pos).toJuego())
                comprobarBotones()
            } catch (e: NullPointerException) {
                _uiState.update { it.copy(error = Constantes.NOGAMES) }
                addJuegoUseCase.invoke(Juego(1,Constantes.S,1.1, LocalDate.now(),Constantes.ACCION,2,true))
                _uiState.update { it.copy(error = null) }
                comprobarBotones()

            }
        }

    }

    fun event(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.addJuego -> addJuego(event.juego)
            MainContract.Event.cambiarEditorMode -> cambiarEditorMode()
            is MainContract.Event.cambiarnombre -> cambiarNombre(event.nombre)
            is MainContract.Event.cargarJuego -> cargarJuego(event.juego)
            MainContract.Event.dameAnterior -> dameAnterior()
            MainContract.Event.dameSiguiente -> dameSiguiente()
            is MainContract.Event.deleteJuego -> deleteJuego(event.juego)
            is MainContract.Event.updateJuego -> updateJuego(event.juego)
            is MainContract.Event.cambiarFecha -> cambiarFecha(event.fecha)
            is MainContract.Event.cambiarPrecio -> cambiarPrecio(event.precio)
            is MainContract.Event.cambiarDiff -> cambiarDiff(event.diff)
            is MainContract.Event.cambiarGenero -> cambiarGen(event.string)
            is MainContract.Event.cambiarMultiplayer -> cambiarMulti(event.mult)
            MainContract.Event.cambiarExtMode -> cambiarExtMode()
        }


    }
    fun cambiarMulti(mult:Boolean){
        _uiState.update {
            it.copy(juego = it.juego.copy(multijugador =mult))
        }
    }
    fun cambiarGen(gen:String){
        _uiState.update {
            it.copy(juego = it.juego.copy(genero =gen))
        }
    }
    fun cambiarDiff(diff:Int){
        _uiState.update {
            it.copy(juego = it.juego.copy(dificultad = diff))
        }
    }


    fun cambiarNombre(nombre: String) {
        _uiState.update {
            it.copy(juego = it.juego.copy(nombre = nombre))
        }
    }

    fun cambiarFecha(fecha: LocalDate) {

            _uiState.update {
                it.copy(juego = it.juego.copy(fecha = fecha))

            }

    }

    fun cambiarPrecio(precio: Double) {
        _uiState.update {
            it.copy(juego = it.juego.copy(precio = precio))
        }
    }

    fun addJuego(juego: Juego) {
        viewModelScope.launch {

            addJuegoUseCase.invoke(juego.copy(id = getLastJuegoUseCase.invoke().id+1))
            comprobarBotones()

        }

    }

    fun cargarJuego(juego: Juego) {

        _uiState.update {
            it.copy(juego = juego)
        }
    }

    fun deleteJuego(juego: Juego) {
        viewModelScope.launch {
            deleteJuegoUseCase.invoke(juego)
            if (pos == 0)
                if (pos == getAllJuegosUseCase.invoke().size - 1)
                    comprobarBotones()
                else
                    dameSiguiente()
            else
                dameAnterior()
        }
    }

    fun updateJuego(juego: Juego) {
        viewModelScope.launch {
            updateJuegoUseCase.invoke(juego)
        }

    }

    fun dameSiguiente() {
        pos++
        comprobarBotones()

    }

    fun dameAnterior() {
        pos--
        comprobarBotones()

    }
    fun cambiarExtMode(){
        if (uiState.value.expanded) {
            _uiState.update {
                it.copy(expanded = false)
            }
        } else {
            _uiState.update {
                it.copy(expanded = true)
            }
        }
    }

    fun cambiarEditorMode() {
        if (uiState.value.editorOn) {
            _uiState.update {
                it.copy(editorOn = false)
            }
        } else {
            _uiState.update {
                it.copy(editorOn = true)
            }
        }

    }

    fun comprobarBotones() {
        viewModelScope.launch {
            cargarJuego(getJuegoUseCase.invoke(pos).toJuego())

            val primeroFlag = getFirstJuegoUseCase.invoke() == uiState.value.juego
            val ultimoFlag = getLastJuegoUseCase.invoke() == uiState.value.juego
            _uiState.update {
                it.copy(
                    ultimo = !ultimoFlag, primero = !primeroFlag
                )
            }


        }


    }
}