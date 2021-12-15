package com.example.prakt13

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AnimeAdapter(private val animeList: List<Anime>): RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    private var list : List<Anime> = listOf()

    fun getList() = list

    fun setList(newList : List<Anime>) {
        list = newList
    }

    class AnimeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun fill(anime: String, character: String, quote: String){
            itemView.findViewById<TextView>(R.id.animeName).text = anime
            itemView.findViewById<TextView>(R.id.characterName).text = character
            itemView.findViewById<TextView>(R.id.characterQuote).text = quote
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.anime_review, parent, false)
        return AnimeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = animeList[position]
        holder.fill(anime.anime!!, anime.character!! ,anime.quote!!)
    }

    override fun getItemCount(): Int = animeList.size
}