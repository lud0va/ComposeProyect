package com.example.juegoscompose.ui.theme.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.juegoscompose.ui.theme.JuegosComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pantalla()
        }
    }

}
@Composable
fun Pantalla(
    viewModel: MainViewModel= hiltViewModel(),
){
    ContenidoPantalla(viewModel)
}
@Composable
fun ContenidoPantalla(
    viewModel: MainViewModel?=null
){

}