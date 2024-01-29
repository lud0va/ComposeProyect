package com.example.juegoscompose.domain.usecases

import com.example.juegoscompose.data.repository.JuegosRepository
import javax.inject.Inject

class GetFirstJuegoUseCase  @Inject constructor(val juegosRepository: JuegosRepository) {
    suspend fun invoke()=juegosRepository.damePrimero()
}