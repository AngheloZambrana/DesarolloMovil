package com.example.roomdbs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdbs.databinding.ActivityPetsBinding

class PetsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPetsBinding
    private val viewModel: PetViewModel by viewModels()
    private lateinit var adapter: PetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupViewModel()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        adapter = PetAdapter(mutableListOf())
        adapter.onPetDeleteClick = { pet ->
            viewModel.deletePet(this, pet)
        }

        binding.rvPets.apply {
            layoutManager = LinearLayoutManager(this@PetsActivity)
            adapter = this@PetsActivity.adapter
        }
    }

    private fun setupViewModel() {
        viewModel.petList.observe(this) { pets ->
            if (pets.isNullOrEmpty()) {
                binding.rvPets.visibility = View.GONE
                binding.tvEmptyPets.visibility = View.VISIBLE
            } else {
                binding.tvEmptyPets.visibility = View.GONE
                binding.rvPets.visibility = View.VISIBLE
                adapter.updateList(pets)
            }
        }

        viewModel.petDeleted.observe(this) { pet ->
            pet?.let {
                adapter.removePet(it)
                if (adapter.itemCount == 0) {
                    binding.rvPets.visibility = View.GONE
                    binding.tvEmptyPets.visibility = View.VISIBLE
                }
            }
        }

        viewModel.loadPets(this)
    }

    private fun setupClickListeners() {
        binding.fabAddPet.setOnClickListener {
            showAddPetDialog()
        }
    }

    private fun showAddPetDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_pet, null)
        val etPetName = dialogView.findViewById<EditText>(R.id.etPetName)
        val etPetType = dialogView.findViewById<EditText>(R.id.etPetType)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Agregar mascota")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val name = etPetName.text.toString().trim()
                val type = etPetType.text.toString().trim()

                if (name.isNotEmpty() && type.isNotEmpty()) {
                    val newPet = Pet(name, type)
                    viewModel.insertPet(this@PetsActivity, newPet)
                } else {
                    Toast.makeText(this@PetsActivity, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()
    }
}
