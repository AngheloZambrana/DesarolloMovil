package com.example.componentes.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.componentes.R
import com.example.componentes.databinding.ActivityMainBinding
import com.example.componentes.ui.components.PageSelector

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEventListeners()
    }

    private fun setupEventListeners() {
        binding.btnShowSelectedPage.setOnClickListener {
            val pageSelector = findViewById<PageSelector>(R.id.pageSelector)
            val selectedPage = pageSelector.getSelectedPage()
            Toast.makeText(this, "Página seleccionada: $selectedPage", Toast.LENGTH_SHORT).show()
        }
    }
}