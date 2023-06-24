package com.example.pokedex.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.pokedex.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val metersTextView = findViewById<TextView>(R.id.meters)
        val newPokemonButton = findViewById<AppCompatButton>(R.id.newButton)
        newPokemonButton.setOnClickListener { getNewPokemon() }
    }

    fun countMeters() {
        //contamos metros
        //imprimimos en pantalla los metros avanzados
        //cuando llegamos a 10
        getNewPokemon()
    }

    private fun getNewPokemon() {
        val newPokemonIntent = Intent(this, PokemonInfoActivity::class.java)
        newPokemonIntent.putExtra("id", 1)
        startActivity(newPokemonIntent)
    }
}