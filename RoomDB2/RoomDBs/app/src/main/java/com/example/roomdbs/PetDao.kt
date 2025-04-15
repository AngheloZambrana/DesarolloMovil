package com.example.roomdbs

import androidx.room.*

@Dao
interface PetDao {
    @Query("SELECT * FROM Pet")
    suspend fun getAll(): List<Pet>

    @Query("SELECT * FROM Pet WHERE id = :id")
    suspend fun getById(id: Int): Pet

    @Insert
    suspend fun insertPet(pet: Pet): Long

    @Update
    suspend fun updatePet(pet: Pet)

    @Delete
    suspend fun deletePet(pet: Pet): Int
}
