package com.example.pokedex.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pokedex.R

class PokemonInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_view)

        //todo: start vm

        initUI()
    }

    private fun initUI(){
        val id = intent.extras?.getInt("id") as Int
        //todo: obtain information
        //todo: get change in info
    }
}