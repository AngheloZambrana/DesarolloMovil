package com.example.roomdbs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PersonDao {
    @Query("SELECT * FROM Person")
    suspend fun getAll(): List<Person>

    @Query("SELECT * FROM Person WHERE id = :id")
    suspend fun getById(id: Int): Person

    @Insert
    suspend fun insertPerson(person: Person): Long

    @Update
    suspend fun updatePerson(person: Person)

    @Delete
    suspend fun deletePerson(person: Person): Int
}