package com.example.roomdbs

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Person::class, Pet::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
    abstract fun petDao(): PetDao
}
