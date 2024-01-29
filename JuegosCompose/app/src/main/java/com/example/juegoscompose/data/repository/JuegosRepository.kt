package com.example.juegoscompose.data.repository

import com.example.juegoscompose.data.JuegoDao
import com.example.juegoscompose.data.model.JuegoEntity
import com.example.juegoscompose.data.model.toJuego
import com.example.juegoscompose.data.model.toJuegoEntity
import com.example.juegoscompose.domain.model.Juego
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class JuegosRepository @Inject constructor(
    private val juegoDao: JuegoDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

) {

    suspend fun getAll():List<Juego>{
        return juegoDao.getAll().map { it.toJuego() }
    }
    suspend fun getJuego(id: Int): JuegoEntity {
        return juegoDao.getJuego(id)


    }
    suspend fun addJuego(juego: Juego) {
        return juegoDao.add(juego.toJuegoEntity())


    }

    suspend fun dameUltimo():Juego{
        return juegoDao.getLastJuego().toJuego();
    }

    suspend fun damePrimero():Juego{
        return juegoDao.getFirstJuego().toJuego();
    }
    suspend fun deleteJuego(juego: Juego) {
        return juegoDao.delete(juego.toJuegoEntity())


    }
    suspend fun updateJuego(juego: Juego) {
        return juegoDao.update(juego.toJuegoEntity())


    }

}