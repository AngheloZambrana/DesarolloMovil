package com.example.geolocalizacion

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
class RecorridosFragment : Fragment() {

    private lateinit var recorridoRepository: RecorridoRepository
    private lateinit var recyclerView: RecyclerView
    private lateinit var recorridoAdapter: RecorridoAdapter
    private lateinit var btnAgregarRuta: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_recorridos, container, false)

        recorridoRepository = RecorridoRepository(requireContext())

        // Configurar RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewRecorridos)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recorridoAdapter = RecorridoAdapter(emptyList()) { recorrido ->
            val bundle = Bundle().apply {
                putSerializable("recorrido", recorrido)  // Asumiendo que `RecorridoDTO` es Serializable
            }

            val mapsFragment = MapsFragment()
            mapsFragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, mapsFragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.adapter = recorridoAdapter

        btnAgregarRuta = view.findViewById(R.id.btnAgregarRuta)
        btnAgregarRuta.setOnClickListener {
            val mapsFragment = MapsFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, mapsFragment)
                .addToBackStack(null)
                .commit()
        }

        // Cargar datos
        cargarRecorridos()

        return view
    }

    private fun cargarRecorridos() {
        lifecycleScope.launch {
            try {
                val recorridos = recorridoRepository.obtenerRecorridos()

                recorridoAdapter.actualizarRecorridos(recorridos)

                if (recorridos.isEmpty()) {
                    Toast.makeText(requireContext(), "No hay recorridos registrados", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("RecorridosFragment", "Error al cargar los recorridos", e)
                Toast.makeText(requireContext(), "Error al cargar los recorridos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

