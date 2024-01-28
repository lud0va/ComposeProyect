package com.example.juegoscompose.domain.usecases

import com.example.juegoscompose.data.repository.JuegosRepository
import com.example.juegoscompose.domain.model.Juego
import javax.inject.Inject

class DeleteJuegoUseCase @Inject constructor(val juegosRepository: JuegosRepository) {
    suspend fun invoke(juego: Juego)=juegosRepository.deleteJuego(juego)
}