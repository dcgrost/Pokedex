package com.example.pokedex.service

import android.telecom.Call
import com.example.pokedex.model.api.Pokemon
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {
    @GET("pokemon/{id}")
    fun getPokemonInfo(@Path("id") id:Int):Class<Pokemon>
}