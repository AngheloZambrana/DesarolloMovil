package com.example.retrofit

import com.google.gson.annotations.SerializedName

typealias Comments = List<Comment>

data class Comment(
    @SerializedName("postId")
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)
