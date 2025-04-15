package com.example.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PostDetailViewModel : ViewModel() {
    private val _postDetails = MutableLiveData<Pair<Post, Comments>>()
    val postDetails: LiveData<Pair<Post, Comments>> = _postDetails

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun loadPostDetails(postId: Int) {
        viewModelScope.launch {
            try {
                val details = PostRepository.getPostDetail(postId)
                _postDetails.postValue(details)
            } catch (e: Exception) {
                _errorMessage.postValue("Error al cargar detalles: ${e.message}")
            }
        }
    }
}