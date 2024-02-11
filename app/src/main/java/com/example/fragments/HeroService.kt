package com.example.fragments

import Hero
import retrofit2.Call
import retrofit2.http.GET

interface HeroService {
    @GET("all.json")
    fun getHeroes(): Call<List<Hero>>
}


