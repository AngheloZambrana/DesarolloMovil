package com.example.roomdbs

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PetViewModel : ViewModel() {
    private val _petList = MutableLiveData<List<Pet>>()
    val petList: LiveData<List<Pet>> = _petList

    private val _petDeleted = MutableLiveData<Pet>()
    val petDeleted: LiveData<Pet> = _petDeleted

    fun loadPets(context: Context) {
        viewModelScope.launch {
            _petList.value = PetRepository.getAllPets(context)
        }
    }

    fun insertPet(context: Context, pet: Pet) {
        viewModelScope.launch {
            PetRepository.insertPet(context, pet)
            loadPets(context)
        }
    }

    fun deletePet(context: Context, pet: Pet) {
        viewModelScope.launch {
            PetRepository.deletePet(context, pet)
            _petDeleted.postValue(pet)
        }
    }
}