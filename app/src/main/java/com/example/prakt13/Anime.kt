package com.example.prakt13

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

data class Anime(
    var anime: String? = null,
    var character: String? = null,
    var quote: String? = null
)