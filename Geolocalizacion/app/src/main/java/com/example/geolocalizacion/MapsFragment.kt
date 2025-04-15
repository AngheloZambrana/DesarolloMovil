package com.example.geolocalizacion


import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.example.geolocalizacion.databinding.FragmentMapBinding
import kotlinx.coroutines.launch


class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val pathPoints = mutableListOf<LatLng>()
    private lateinit var recorridoRepository: RecorridoRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recorridoRepository = RecorridoRepository(requireContext())

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.map)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        // Acción del botón "Terminar recorrido"
        view.findViewById<Button>(R.id.btnTerminarRecorrido).setOnClickListener {
            // Guardar el recorrido en la base de datos
            guardarRecorrido()

            parentFragmentManager.popBackStack()
        }
    }

    private fun guardarRecorrido() {
        val fecha = System.currentTimeMillis().toString() // Fecha en milisegundos
        val puntos = pathPoints.joinToString(",") { "${it.latitude},${it.longitude}" } // Convertir las coordenadas a una cadena
        val puntoInicio = pathPoints.firstOrNull()?.let { "${it.latitude},${it.longitude}" } ?: ""
        val puntoFinal = pathPoints.lastOrNull()?.let { "${it.latitude},${it.longitude}" } ?: ""

        val recorrido = RecorridoDTO(fecha = fecha, puntos = puntos, puntoInicio = puntoInicio, puntoFinal = puntoFinal)

        lifecycleScope.launch {
            recorridoRepository.insertarRecorrido(recorrido)
        }
    }

    // Método para mostrar el recorrido en el mapa
    fun mostrarRecorridoEnMapa(recorrido: RecorridoDTO) {
        // Obtener la lista de puntos a partir de la cadena de puntos
        val puntos = recorrido.puntos.split(",")

        // Asegurarnos de que la lista tiene un número par de elementos
        if (puntos.size % 2 == 0) {
            val latLngList = mutableListOf<LatLng>()

            // Crear los puntos LatLng a partir de la lista
            for (i in puntos.indices step 2) {
                try {
                    val lat = puntos[i].toDouble()
                    val lng = puntos[i + 1].toDouble()
                    latLngList.add(LatLng(lat, lng))
                } catch (e: NumberFormatException) {
                    Log.e("MapsFragment", "Coordenada no válida: ${puntos[i]}, ${puntos[i + 1]}")
                }
            }

            // Verificar si la lista de puntos está vacía
            if (latLngList.isNotEmpty()) {
                // Agregar los puntos a la polilínea en el mapa
                map.addPolyline(
                    PolylineOptions()
                        .addAll(latLngList)
                        .width(8f)
                        .color(android.graphics.Color.RED)
                )

                // Mover la cámara al primer punto
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngList.first(), 17f))
            } else {
                Log.e("MapsFragment", "No se encontraron puntos válidos.")
            }
        } else {
            Log.e("MapsFragment", "La cantidad de puntos es inválida.")
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
            return
        }

        val recorrido = arguments?.getSerializable("recorrido") as? RecorridoDTO
        recorrido?.let {
            mostrarRecorridoEnMapa(it)
        }

        map.isMyLocationEnabled = true
        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            interval = 3000 // cada 3 segundos
            fastestInterval = 2000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                for (location in result.locations) {
                    updatePath(location)
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                requireActivity().mainLooper
            )
        }
    }

    private fun updatePath(location: Location) {
        val newPoint = LatLng(location.latitude, location.longitude)
        pathPoints.add(newPoint)

        if (pathPoints.size == 1) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(newPoint, 17f))
        }

        if (pathPoints.size >= 2) {
            map.addPolyline(
                PolylineOptions()
                    .addAll(pathPoints)
                    .width(8f)
                    .color(android.graphics.Color.BLUE)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}
