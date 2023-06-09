package com.example.kotlinv2.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin.model.Movie
import com.example.kotlinv2.MovieDetailActivity
import com.example.kotlinv2.R
import com.example.kotlinv2.model.MovieDetail

class MovieDetailAdapter(private val context: Context, private val movieList: ArrayList<MovieDetail>) :
    RecyclerView.Adapter<MovieDetailAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieTitle: TextView = itemView.findViewById(R.id.searchMovieTitle)
        val movieReleaseDate: TextView = itemView.findViewById(R.id.searchMovieReleaseDate)
        val movieImageView: ImageView = itemView.findViewById(R.id.searchMovieImageView)
        val movieVoteAverage: TextView = itemView.findViewById(R.id.searchMovieVoteAverage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.search_movie_view, parent, false)
        return ViewHolder((itemView))
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentMovie = movieList[position]

        holder.movieTitle.text = currentMovie.title
        holder.movieReleaseDate.text = currentMovie.release_date
        holder.movieVoteAverage.text = currentMovie.vote_average.toString() + "/10"

        Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + currentMovie.poster_path).centerCrop()
            .into(holder.movieImageView)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra("movieId", currentMovie.id)
            context.startActivity(intent)
        }

    }
}