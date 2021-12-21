package com.example.prakt13

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface retrofit_svc {
    @GET("api/quotes/")
    fun getAllQuotes() : Call<List<Anime>>

    @GET("api/quotes/character")
    fun getCharacterQuotes(@Query("name") characterName:String): Call<List<Anime>>

    companion object
    {
        var retrofitSvc : retrofit_svc? = null
        fun getInstance(): retrofit_svc
        {
            if(retrofitSvc == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://animechan.vercel.app/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitSvc = retrofit.create(retrofit_svc::class.java)
            }
            return retrofitSvc!!
        }

    }

}