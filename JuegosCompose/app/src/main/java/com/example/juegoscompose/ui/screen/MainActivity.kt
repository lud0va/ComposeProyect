package com.example.juegoscompose.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.juegoscompose.domain.model.Juego
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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
        if (state.editorOn) {
        }
        editor(state,viewModel = viewModel, Modifier.align(Alignment.End))
        Spacer(modifier = Modifier.padding(16.dp))
        nombreJuego(state,viewModel)
        Spacer(modifier = Modifier.padding(16.dp))
        precioJuego(state,viewModel)
        Spacer(modifier = Modifier.padding(16.dp))

        fechaJuego(state,viewModel)


        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            buttonAdd(state,viewModel)
            Spacer(modifier = Modifier.weight(1f))
            buttonDelete(state,viewModel)
            Spacer(modifier = Modifier.weight(1f))
            buttonMod(state,viewModel)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            backBtn(state,viewModel)
            Spacer(modifier = Modifier.padding(16.dp))
            nextBtn(state,viewModel)

        }


    }


}

@Composable
fun editor(state: MainContract.State,viewModel: MainViewModel?, align: Modifier) {

    Column(modifier = align) {

            Switch(
                checked = state.editorOn,
                onCheckedChange = {
                    viewModel?.cambiarEditorMode()


                },

                )
        }



}

@Composable
fun nombreJuego(state: MainContract.State,viewModel: MainViewModel?) {


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
fun precioJuego(state: MainContract.State,viewModel: MainViewModel?) {


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
fun fechaJuego(state: MainContract.State,viewModel: MainViewModel?) {


        TextField(
            value = state.juego.fecha.toString(),
            onValueChange = {
                viewModel?.event(MainContract.Event.cambiarFecha(LocalDate.parse(it)))

            },
            singleLine = true,
            enabled = state.editorOn,
            modifier = Modifier.fillMaxWidth()

        )

}

@Composable
fun buttonAdd(state: MainContract.State,viewModel: MainViewModel?) {

    Button(
        onClick = {
            viewModel?.event(MainContract.Event.addJuego(state.juego))
            viewModel?.event(MainContract.Event.dameSiguiente)      },
        content = { Text(text = "Add") })

}

@Composable
fun buttonDelete(state: MainContract.State,viewModel: MainViewModel?) {
    Button(onClick = { viewModel?.event(MainContract.Event.deleteJuego(state.juego))
        viewModel?.event(MainContract.Event.dameAnterior)
    },
        content = { Text(text = "Delete") })


}

@Composable
fun buttonMod(state: MainContract.State,viewModel: MainViewModel?) {

    Button(onClick = { viewModel?.event(MainContract.Event.updateJuego(state.juego)) },
        enabled = state.editorOn,
        content = { Text(text = "Update") })


}

@Composable
fun nextBtn(state: MainContract.State,viewModel: MainViewModel?) {
    Button(onClick = { viewModel?.event(MainContract.Event.dameSiguiente) },
        enabled = state.primero,
        content = { Text(text = "Next") })


}

@Composable
fun backBtn(state: MainContract.State,viewModel: MainViewModel?) {

    Button(onClick = {  viewModel?.event(MainContract.Event.dameAnterior) },
        enabled = state.ultimo,
        content = { Text(text = "Back") })


}


