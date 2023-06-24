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

    //inicializa la variable viewModel desde PokemonInfoViewModel
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

        //obtiene los extras enviados desde main que en este caso es el id
        val id = intent.extras?.getInt("id") as Int
        //obtiene la informacion usando el id
        viewModel.getPokemonInfo(id)

        //obtiene los cambios y los muestra en sus respectivos campos
        viewModel.pokemonInfo.observe(this, Observer { pokemon ->
            pokemonNameTV.text = pokemon.name
            pokemonHeightTV.text = "Altura: ${pokemon.height / 10.0} m"
            pokemonWeightTV.text = "Peso: ${pokemon.weight / 10.0} kg"

            //usa Glide para interpretar y mostrar la imagen obtenida
            Glide.with(this).load(pokemon.sprites.frontDefault).into(pokemonImageIV)
        })
    }
}