package com.example.prakt13

class main_repo(private val retrofitSvc: retrofit_svc) {
    fun getAllQuotes() = retrofitSvc.getAllQuotes()
    fun getCharacterQuotes(characterName: String) = retrofitSvc.getCharacterQuotes(characterName)
}