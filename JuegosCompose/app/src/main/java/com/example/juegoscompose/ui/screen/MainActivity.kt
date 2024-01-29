package com.example.juegoscompose.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.juegoscompose.Utils.Constantes
import com.example.juegoscompose.domain.model.Juego
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pantalla()
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Pantalla(
    viewModel: MainViewModel = hiltViewModel(),

    ) {
    val state by viewModel.uiState.collectAsState()
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        ContenidoPantalla(
            state,
            viewModel, Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }


}


@Composable
fun ContenidoPantalla(
    state: MainContract.State,
    viewModel: MainViewModel? = null,
    align: Modifier,

    ) {

    Column(modifier = align) {

        editor(state, viewModel = viewModel, Modifier.align(Alignment.End))
        Spacer(modifier = Modifier.padding(16.dp))
        nombreJuego(state, viewModel)
        Spacer(modifier = Modifier.padding(6.dp))
        precioJuego(state, viewModel)
        Spacer(modifier = Modifier.padding(6.dp))

        fechaJuego(state, viewModel)
        Spacer(modifier = Modifier.padding(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            multiplayerJuego(state = state, viewModel = viewModel)
            generoJuego(state = state, viewModel = viewModel)


        }
        Spacer(modifier = Modifier.padding(16.dp))
        dificultadJuego(state = state, viewModel =viewModel )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            buttonAdd(state, viewModel)
            Spacer(modifier = Modifier.weight(1f))
            buttonDelete(state, viewModel)
            Spacer(modifier = Modifier.weight(1f))
            buttonMod(state, viewModel)
        }

        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            backBtn(state, viewModel)
            Spacer(modifier = Modifier.padding(16.dp))
            nextBtn(state, viewModel)

        }
        if (state.error != null) {
            Snackbar(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(text = state.error)
            }
        }

    }


}

@Composable
fun editor(state: MainContract.State, viewModel: MainViewModel?, align: Modifier) {

    Column(modifier = align) {

        Switch(
            checked = state.editorOn,
            onCheckedChange = {
                viewModel?.event(MainContract.Event.cambiarEditorMode)


            },

            )
    }


}

@Composable
fun nombreJuego(state: MainContract.State, viewModel: MainViewModel?) {


    TextField(
        value = state.juego.nombre,

        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        onValueChange = {
            viewModel?.event(MainContract.Event.cambiarnombre(it))

        },
        singleLine = true,
        enabled = state.editorOn,
        modifier = Modifier.fillMaxWidth()

    )

}

@Composable
fun precioJuego(state: MainContract.State, viewModel: MainViewModel?) {


    TextField(
        value = state.juego.precio.toString(),

        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = {
            viewModel?.event(MainContract.Event.cambiarPrecio(it.toDouble()))
        },
        singleLine = true,
        enabled = state.editorOn,
        modifier = Modifier.fillMaxWidth()

    )


}

@Composable
fun dificultadJuego(state: MainContract.State, viewModel: MainViewModel?){
    Slider( valueRange = 1f..3f ,
        enabled = state.editorOn,

        steps = 1,
        value = state.juego.dificultad.toFloat(),
        onValueChange = {
           viewModel?.event(MainContract.Event.cambiarDiff(it.toInt()))
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    )


}




@Composable
fun generoJuego(state: MainContract.State, viewModel: MainViewModel?) {

    Box {

        TextButton( modifier = Modifier
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.primary)
            .clip(RoundedCornerShape(30.dp))
            ,

                onClick = { viewModel?.event(MainContract.Event.cambiarExtMode)}) {
            Text(Constantes.GENERO_ +state.juego.genero, color = MaterialTheme.colorScheme.onTertiary)
        }
        DropdownMenu(expanded = state.expanded, onDismissRequest = { }) {
            DropdownMenuItem(enabled = state.editorOn,

                text = { Text(text = Constantes.ACCION) },
                onClick = {
                    viewModel?.event(MainContract.Event.cambiarGenero(Constantes.ACCION))
                    viewModel?.event(MainContract.Event.cambiarExtMode)
                })
            DropdownMenuItem(enabled = state.editorOn,
                text = { Text(text = Constantes.MIEDO) },
                onClick = {
                    viewModel?.event(MainContract.Event.cambiarGenero(Constantes.MIEDO))
                    viewModel?.event(MainContract.Event.cambiarExtMode)
                })
            DropdownMenuItem(enabled = state.editorOn,
                text = { Text(text = Constantes.AVENTURA) },
                onClick = {
                    viewModel?.event(MainContract.Event.cambiarGenero(Constantes.AVENTURA))
                    viewModel?.event(MainContract.Event.cambiarExtMode)
                })
            DropdownMenuItem(
                text = { Text(text = Constantes.SALIR) },
                onClick = {
                    viewModel?.event(MainContract.Event.cambiarExtMode)
                })
        }
    }
}



@Composable
fun multiplayerJuego(state: MainContract.State, viewModel: MainViewModel?) {
    Text(text = Constantes.MULTIPLAYER, fontWeight = FontWeight.Bold)
    Checkbox(checked = state.juego.multijugador, enabled = state.editorOn,onCheckedChange = {
        viewModel?.event(MainContract.Event.cambiarMultiplayer(it))
    })
}

@Composable
fun fechaJuego(state: MainContract.State, viewModel: MainViewModel?) {


    TextField(
        value = state.juego.fecha.toString(),
        onValueChange = {
            viewModel?.event(MainContract.Event.cambiarFecha(LocalDate.parse(it)))

        },
        singleLine = true,
        enabled = false,
        modifier = Modifier.fillMaxWidth()

    )

}



@Composable
fun buttonAdd(state: MainContract.State, viewModel: MainViewModel?) {

    Button(
        onClick = {
            viewModel?.event(MainContract.Event.addJuego(state.juego))
        },
        content = { Text(text =Constantes.ADD) })

}



@Composable
fun buttonDelete(state: MainContract.State, viewModel: MainViewModel?) {
    var flag = true
    if (state.juego.id == 1) {
        flag = false
    }
    Button(onClick = {
        viewModel?.event(MainContract.Event.deleteJuego(state.juego))
    },
        enabled = flag,
        content = { Text(text = Constantes.DELETE) })


}



@Composable
fun buttonMod(state: MainContract.State, viewModel: MainViewModel?) {

    Button(onClick = { viewModel?.event(MainContract.Event.updateJuego(state.juego)) },
        enabled = state.editorOn,
        content = { Text(text = Constantes.UPDATE) })


}


@Composable
fun nextBtn(state: MainContract.State, viewModel: MainViewModel?) {
    Button(onClick = { viewModel?.event(MainContract.Event.dameSiguiente) },
        enabled = state.ultimo,
        content = { Text(text = Constantes.NEXT) })


}



@Composable
fun backBtn(state: MainContract.State, viewModel: MainViewModel?) {

    Button(onClick = { viewModel?.event(MainContract.Event.dameAnterior) },
        enabled = state.primero,
        content = { Text(text = Constantes.BACK) })


}


