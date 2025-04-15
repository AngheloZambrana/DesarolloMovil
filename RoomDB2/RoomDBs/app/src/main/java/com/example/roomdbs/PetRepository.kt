package com.example.roomdbs

import android.content.Context


object PetRepository {
    suspend fun getAllPets(context: Context): List<Pet> {
        return RoomRepository.getRoomInstance(context).petDao().getAll()
    }

    suspend fun insertPet(context: Context, pet: Pet): Long {
        return RoomRepository.getRoomInstance(context).petDao().insertPet(pet)
    }

    suspend fun deletePet(context: Context, pet: Pet): Int {
        return RoomRepository.getRoomInstance(context).petDao().deletePet(pet)
    }
}
