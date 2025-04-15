package com.example.retrofit

import com.google.gson.annotations.SerializedName

typealias Posts = ArrayList<Post>

data class Post(
    @SerializedName("userId")
    val userID: Int,
    val id: Int,
    val title: String,
    val body: String
)
