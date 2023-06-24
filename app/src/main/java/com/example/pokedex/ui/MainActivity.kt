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
    //variables de localizacion
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private var distanceTraveled = 0f
    private var targetDistance = 10f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //verificamos permisos
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //si no tiene el permiso lo pide
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
        }
        //inicializamos el servicio
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            //si la localizacion cambia
            override fun onLocationChanged(location: Location) {
                //aumenta a la variable lo que aumento
                distanceTraveled += location.distanceTo(location)
                //llama al metodo para actualizar la información de la distancia
                updateDistance()
            }
        }
        val newPokemonButton = findViewById<Button>(R.id.newButton)
        newPokemonButton.setOnClickListener { getNewPokemon() }
        //debug para aumentar la distancia
        val debbugButton = findViewById<Button>(R.id.debugButton)
        debbugButton.setOnClickListener { addDistance() }
    }

    //funcion debug para aumentar la distancia
    private fun addDistance() {
        distanceTraveled++
        updateDistance()
    }

    override fun onResume() {
        super.onResume()
        //vuelve a registrar el Listener para rescibir actualizaciones de ubicación
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
        //Detiene las actualizaciones de ubicación cuando la actividad esté en pausa
        locationManager.removeUpdates(locationListener)
    }

    //metodo para
    private fun updateDistance() {
        //si la distancia recorrida es menor a la distancia maxima
        if (distanceTraveled < targetDistance) {
            val formattedDistance = String.format("%.0f", distanceTraveled)
            val targetDistanceString = String.format("%.0f", targetDistance)
            val distanceText = "Metros recorridos: $formattedDistance/$targetDistanceString m"
            //Actualzia el texto de la activity
            val metersTextView = findViewById<TextView>(R.id.meters)
            metersTextView.text = distanceText
        } else {
            //cuando es 10m
            //resetea contador
            distanceTraveled = 0f
            //lanza toast
            Toast.makeText(this, "Pokemón encontrado", Toast.LENGTH_SHORT).show()
            //obtiene un nuevo pokemon
            getNewPokemon()
        }
    }

    private fun getNewPokemon() {
        //lanza la activity de info pasandole un numero aletorio generado como id
        val newPokemonIntent = Intent(this, PokemonInfoActivity::class.java)
        newPokemonIntent.putExtra("id", randomPokemon())
        startActivity(newPokemonIntent)
    }

    private fun randomPokemon(): Int {
        //regresa un numero aleatorio entre 1 y 1010
        return Random.nextInt(1, 1010)
    }
}