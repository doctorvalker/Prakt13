package com.example.prakt13

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prakt13.databinding.AnimeReviewBinding

class AnimeAdapter: RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    var quotes = mutableListOf<Anime>()

    fun setQuoteList(quotes: List<Anime>) {
        this.quotes = quotes.toMutableList()
        notifyDataSetChanged()
    }

    fun getList() = quotes

    class AnimeViewHolder(val binding: AnimeReviewBinding): RecyclerView.ViewHolder(binding.root) {

        /*fun fill(anime: String, character: String, quote: String){
            itemView.findViewById<TextView>(R.id.animeName).text = anime
            itemView.findViewById<TextView>(R.id.characterName).text = character
            itemView.findViewById<TextView>(R.id.characterQuote).text = quote
        }*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AnimeReviewBinding.inflate(inflater, parent, false)
        //val itemView = LayoutInflater.from(parent.context).inflate(R.layout.anime_review, parent, false)
        return AnimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = quotes[position]
        holder.binding.animeName.text = anime.anime!!
        holder.binding.characterName.text = anime.character!!
        holder.binding.characterQuote.text = anime.quote!!
    }

    override fun getItemCount(): Int = quotes.size
}