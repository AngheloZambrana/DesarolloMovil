package com.example.geolocalizacion



import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecorridoDao {

    @Insert
    suspend fun insertarRecorrido(recorrido: RecorridoDTO)

    @Query("SELECT * FROM recorridos")
    suspend fun obtenerRecorridos(): List<RecorridoDTO>
}
