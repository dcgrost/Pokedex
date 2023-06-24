package com.example.pokedex.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        Log.i("button click", id.toString())
        //obtain information

        //todo: get change in info
    }
}