// RecorridoRepository.kt
package com.example.geolocalizacion

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecorridoRepository(context: Context) {

    private val recorridoDao = RecorridoDatabase.getDatabase(context).recorridoDao()

    suspend fun insertarRecorrido(recorrido: RecorridoDTO) {
        withContext(Dispatchers.IO) {
            recorridoDao.insertarRecorrido(recorrido)
        }
    }

    suspend fun obtenerRecorridos(): List<RecorridoDTO> {
        return withContext(Dispatchers.IO) {
            recorridoDao.obtenerRecorridos()
        }
    }
}
