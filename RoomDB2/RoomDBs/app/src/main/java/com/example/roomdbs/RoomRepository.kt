package com.example.roomdbs

import android.content.Context
import androidx.room.Room


object RoomRepository {
    fun getRoomInstance(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "prueba-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}