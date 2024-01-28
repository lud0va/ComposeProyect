package com.example.juegoscompose.domain.usecases

import com.example.juegoscompose.data.repository.JuegosRepository
import com.example.juegoscompose.domain.model.Juego
import javax.inject.Inject

class GetJuegoUseCase @Inject constructor(val juegosRepository: JuegosRepository) {
    suspend fun invoke(id: Int)=juegosRepository.getJuego(id)
}