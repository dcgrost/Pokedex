package com.example.pokedex.ui

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.pokedex.R

class PokemonInfoActivity : AppCompatActivity() {

    lateinit var viewModel: PokemonInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_view)

        viewModel = ViewModelProvider(this)[PokemonInfoViewModel::class.java]

        initUI()
    }

    private fun initUI() {
        val pokemonNameTV = findViewById<TextView>(R.id.pokemonName)
        val pokemonImageIV = findViewById<ImageView>(R.id.pokemonImage)
        val pokemonHeightTV = findViewById<TextView>(R.id.pokemonHeight)
        val pokemonWeightTV = findViewById<TextView>(R.id.pokemonWeight)

        val id = intent.extras?.getInt("id") as Int
        Log.i("button click", id.toString())
        //obtain information
        viewModel.getPokemonInfo(id)

        //get change in info
        viewModel.pokemonInfo.observe(this, Observer { pokemon ->
            pokemonNameTV.text = pokemon.name
            pokemonHeightTV.text = "Altura: ${pokemon.height / 10.0} m"
            pokemonWeightTV.text = "Peso: ${pokemon.weight / 10.0} kg"

            Glide.with(this).load(pokemon.sprites.frontDefault).into(pokemonImageIV)
        })
    }
}