package com.example.roomdbs

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "prueba-db"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

        val personDao = db.personDao()
        personDao.insertPerson(
            Person(
                "Juan",
                "Perez",
                25,
                "juan@test.com",
                "1234567890",
                "1996-01-01"
            )
        )
        val person = personDao.getById(1)
        Log.d("DB", "Persona insertada: $person")

        val petDao = db.petDao()
        petDao.insertPet(
            Pet(
                name = "Buddy",
                type = "Dog",
                personId = 1  // Id del Juan
            )
        )

        val pets = petDao.getByPersonId(1)
        Log.d("DB", "John's pets: $pets")
    }
}
