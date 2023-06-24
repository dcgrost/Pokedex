package com.example.pokedex.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pokedex.R
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private var distanceTraveled = 0f
    private var targetDistance = 10f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
        }

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                distanceTraveled += location.distanceTo(location) // Calcular la distancia recorrida
                updateDistanceText()
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        }
        val newPokemonButton = findViewById<AppCompatButton>(R.id.newButton)
        newPokemonButton.setOnClickListener { getNewPokemon() }
        val debbugButton = findViewById<Button>(R.id.button)
        debbugButton.setOnClickListener { addDistance() }
    }

    private fun addDistance() {
        distanceTraveled++
        updateDistanceText()
    }

    override fun onResume() {
        super.onResume()
        // Registrar el LocationListener para recibir actualizaciones de ubicación
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000,
                1f,
                locationListener
            )
        }
    }

    override fun onPause() {
        super.onPause()
        // Detener las actualizaciones de ubicación cuando la actividad esté en pausa
        locationManager.removeUpdates(locationListener)
    }

    private fun updateDistanceText() {
        if (distanceTraveled < targetDistance) {
            val formattedDistance = String.format("%.0f", distanceTraveled)
            val targetDistanceString = String.format("%.0f", targetDistance)
            val distanceText = "Metros recorridos: $formattedDistance/$targetDistanceString m"
            // Actualizar la interfaz de usuario con el texto de distancia
            val metersTextView = findViewById<TextView>(R.id.meters)
            metersTextView.text = distanceText
        } else {
            distanceTraveled = 0f
            Toast.makeText(this, "Pokemón encontrado", Toast.LENGTH_SHORT).show()
            getNewPokemon()
        }
    }

    fun countMeters() {
        //cuando llegamos a 10
        getNewPokemon()
    }

    private fun getNewPokemon() {
        val newPokemonIntent = Intent(this, PokemonInfoActivity::class.java)
        newPokemonIntent.putExtra("id", randomPokemon())
        startActivity(newPokemonIntent)
    }

    private fun randomPokemon(): Int {
        return Random.nextInt(1, 1010)
    }
}