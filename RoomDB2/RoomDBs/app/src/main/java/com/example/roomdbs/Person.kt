package com.example.roomdbs

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
data class Person(
    var name: String,
    var lastName: String,
    var age: Int,
    var email: String,
    var phone: String,
    var birthDate: String
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    constructor() : this(
        "",
        "",
        0,
        "",
        "",
        ""
    )
}
