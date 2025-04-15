package com.example.roomdbs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdbs.databinding.ItemPetBinding

class PetAdapter(
    private var pets: MutableList<Pet>
) : RecyclerView.Adapter<PetAdapter.PetViewHolder>() {

    var onPetDeleteClick: ((Pet) -> Unit)? = null

    inner class PetViewHolder(private val binding: ItemPetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pet: Pet) {
            binding.tvPetName.text = pet.name
            binding.tvPetType.text = pet.type
            binding.btnDeletePet.setOnClickListener {
                onPetDeleteClick?.invoke(pet)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val binding = ItemPetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.bind(pets[position])
    }

    override fun getItemCount(): Int = pets.size

    fun updateList(newPets: List<Pet>) {
        pets.clear()
        pets.addAll(newPets)
        notifyDataSetChanged()
    }

    fun removePet(pet: Pet) {
        val position = pets.indexOfFirst { it.id == pet.id }
        if (position != -1) {
            pets.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
