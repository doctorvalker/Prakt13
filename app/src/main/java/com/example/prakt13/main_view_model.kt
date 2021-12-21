package com.example.prakt13

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class main_view_model(private val repo: main_repo): ViewModel() {
    val quoteList = MutableLiveData<List<Anime>>()
    val errorMsg = MutableLiveData<String>()

    fun getAllQuotes()
    {
        val response = repo.getAllQuotes()
        response.enqueue(object: Callback<List<Anime>>{
            override fun onResponse(call: Call<List<Anime>>, response: Response<List<Anime>>) {
                quoteList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Anime>>, t: Throwable) {
                errorMsg.postValue(t.message)
            }
        })
    }
    fun getCharacterQuotes(characterName: String) {
        val response = repo.getCharacterQuotes(characterName)

        response.enqueue(object: Callback<List<Anime>> {
            override fun onResponse(call: Call<List<Anime>>, response: Response<List<Anime>>) {
                quoteList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Anime>>, t: Throwable) {
                errorMsg.postValue(t.message)
            }
        })
    }

}

class view_model_factory constructor(private val repo: main_repo): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(main_view_model::class.java)) {
            main_view_model(this.repo) as T
        }
        else {
            throw IllegalArgumentException("ViewModel Not Found")
        }

    }
}