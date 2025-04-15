package com.example.pruebaapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var tvWelcome: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        tvWelcome = findViewById(R.id.tvWelcome)

        val username = intent.getStringExtra("USERNAME")
        tvWelcome.text = "Bienvenido $username"
    }
}