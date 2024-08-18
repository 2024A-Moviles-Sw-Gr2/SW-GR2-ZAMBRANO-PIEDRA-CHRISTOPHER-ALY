package com.example.examen02_mecanicaauto

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GoogleMaps : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private var mecanicas: ArrayList<MecanicaModel> = arrayListOf()
    private var currentMecanicaIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps)

        mecanicas = BaseDatosCompanion.tables!!.obtenerMecanicas()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val botonNextFarmacia = findViewById<Button>(R.id.btn_siguiente)
        botonNextFarmacia.setOnClickListener {
            currentMecanicaIndex = (currentMecanicaIndex + 1) % mecanicas.size
            mostrarUbicacionMecanica(mecanicas[currentMecanicaIndex])
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        if (mecanicas.isNotEmpty()) {
            mostrarUbicacionMecanica(mecanicas[currentMecanicaIndex])
        }
    }

    private fun mostrarUbicacionMecanica(mecanica: MecanicaModel) {
        val ubicacion = LatLng(mecanica.latitudMecanica, mecanica.longitudMecanica)
        map.clear()
        map.addMarker(MarkerOptions().position(ubicacion).title(mecanica.nombreMecanica))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15f))
    }
}
