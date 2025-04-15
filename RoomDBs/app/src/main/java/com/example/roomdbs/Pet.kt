package com.example.roomdbs

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pet(
    var name: String,
    var type: String,
    var personId: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
