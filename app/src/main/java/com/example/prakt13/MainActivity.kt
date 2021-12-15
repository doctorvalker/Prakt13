package com.example.prakt13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val characterName = findViewById<EditText>(R.id.editTextCharacter)
        val findQuote = findViewById<Button>(R.id.buttonFind)

        val retrofit = Retrofit.Builder().baseUrl("https://animechan.vercel.app/api/quotes/").
        addConverterFactory(GsonConverterFactory.create()).build()

        val apiAnime = retrofit.create(APIAnime::class.java)

        val db = Room.databaseBuilder(applicationContext,  DataBase::class.java, "database").build()
        val animeDao = db.DBDao()

        val layoutManager = LinearLayoutManager(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewQuotes)
        recyclerView.layoutManager = layoutManager

        GlobalScope.launch(Dispatchers.IO) {
            val adapter = AnimeAdapter(
                animeDao.getAll().map{ Anime().apply {anime = it.anime; character = it.character; quote = it.quote} })
            launch(Dispatchers.Main) {
                recyclerView.adapter = adapter
            }
        }

        findQuote.setOnClickListener {

            val character = characterName.text.toString()

            apiAnime.getAnime(character).enqueue(object : Callback<MutableList<Anime>>{
                override fun onResponse(call: Call<MutableList<Anime>>, response: Response<MutableList<Anime>>) {
                    val result = response.body()
                    if (result != null) {
                        GlobalScope.launch(Dispatchers.IO) {
                            animeDao.deleteAll()
                            animeDao.insert(
                                *result.toList().map {DataBaseModel(it.anime!!, it.character!!, it.quote!!)}.toTypedArray()
                            )
                        }
                        val adapter = AnimeAdapter(result)
                        recyclerView.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<MutableList<Anime>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            })

        }
    }
}