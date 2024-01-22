package com.example.juegoscompose.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

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
    viewModel: MainViewModel = hiltViewModel(),
){
    ContenidoPantalla(viewModel)
}
@Composable
fun ContenidoPantalla(
    viewModel: MainViewModel?=null
){

}