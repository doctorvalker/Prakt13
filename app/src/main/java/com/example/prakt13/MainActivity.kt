package com.example.prakt13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.prakt13.databinding.ActivityMainBinding
import kotlinx.coroutines.*


@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {
    lateinit var viewModel: main_view_model
    private val retrofitSvc = retrofit_svc.getInstance()
    private lateinit var binding: ActivityMainBinding
    private val adapter = AnimeAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, view_model_factory(main_repo(retrofitSvc))).get(main_view_model::class.java)
        binding.recyclerViewQuotes.adapter = adapter

        val characterName = findViewById<EditText>(R.id.editTextCharacter)
        val findQuote = findViewById<Button>(R.id.buttonFind)

        val db = Room.databaseBuilder(applicationContext,  DataBase::class.java, "database").build()
        val animeDao = db.DBDao()

        val layoutManager = LinearLayoutManager(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewQuotes)
        recyclerView.layoutManager = layoutManager

        GlobalScope.launch(Dispatchers.IO) {
            adapter.setQuoteList(animeDao.getAll().map{ Anime().apply {anime = it.anime; character = it.character; quote = it.quote} })
        }

        findQuote.setOnClickListener {
            if(characterName.text.toString() != "")
            {
                viewModel.quoteList.observe(this, {
                    adapter.setQuoteList(it)
                })
                viewModel.errorMsg.observe(this, {

                })
                viewModel.getCharacterQuotes(characterName.text.toString())
            }
        }
    }
}