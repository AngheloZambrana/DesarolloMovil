// RecorridoDatabase.kt
package com.example.geolocalizacion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecorridoDTO::class], version = 1, exportSchema = false)
abstract class RecorridoDatabase : RoomDatabase() {

    abstract fun recorridoDao(): RecorridoDao

    companion object {
        @Volatile
        private var INSTANCE: RecorridoDatabase? = null

        fun getDatabase(context: Context): RecorridoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecorridoDatabase::class.java,
                    "recorrido_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
