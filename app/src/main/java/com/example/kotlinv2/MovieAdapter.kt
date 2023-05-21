package com.example.kotlinv2

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

class MovieAdapter(private val context: Context, private val movieList: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)
        val movieReleaseDate: TextView = itemView.findViewById(R.id.movieReleaseDate)
        val movieImageView: ImageView = itemView.findViewById(R.id.movieImageView)
        val movieVoteAverage: TextView = itemView.findViewById(R.id.movieVoteAverage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.movie_view, parent, false)
        return ViewHolder((itemView))
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentMovie = movieList[position]

        holder.movieTitle.text = currentMovie.title.toString()
        holder.movieReleaseDate.text = currentMovie.release_date.toString()
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