package com.example.kotlinv2

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin.model.MovieResponse
import com.example.kotlinv2.fragments.BASE_URL
import com.example.kotlinv2.model.MovieDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MovieDetailActivity : AppCompatActivity() {

    private lateinit var myAdapter: MovieAdapter
    private lateinit var similarMoviesRecyclerView: RecyclerView
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // Changing default backgroudn color
        this.window.decorView.setBackgroundColor(Color.BLACK)

        similarMoviesRecyclerView = findViewById(R.id.similarMoviesRecyclerView)
        similarMoviesRecyclerView.setHasFixedSize(true)
        similarMoviesRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val extras = intent.extras
        if (extras != null) {
            movieId = extras.getInt("movieId")
            Log.e("Movie id: ", movieId.toString())
            val retrofitBuilder =
                Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build().create(ApiInterface::class.java)
            getMovieBasedOnId(this, retrofitBuilder, movieId)
            getSimilarMovies(this, retrofitBuilder, movieId)
        } else {
            Log.d("MovieDetailsActivity", "Error, please give a movie ID")
        }
    }

    private fun getMovieBasedOnId(context: Context, retrofitBuilder: ApiInterface, movieId: Int) {
        val retrofitMovie =
            retrofitBuilder.getMovieBasedOnId(movieId, "53ee22b69f31943882d306c2ba5fb1f9", "en-US")

        retrofitMovie.enqueue(object : Callback<MovieDetail?> {
            override fun onResponse(call: Call<MovieDetail?>, response: Response<MovieDetail?>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    Log.e("Response", responseBody.toString())
                    val movieDetailImageView = findViewById<ImageView>(R.id.movieDetailImageView)
                    val movieDetailTitle = findViewById<TextView>(R.id.movieDetailTitle)
                    val movieDetailReleaseDate = findViewById<TextView>(R.id.movieDetailReleaseDate)
                    val movieDetailVoteAverage = findViewById<TextView>(R.id.movieDetailVoteAverage)
                    val movieDetailOverview = findViewById<TextView>(R.id.movieDetailOverview)

                    Glide.with(context)
                        .load("https://image.tmdb.org/t/p/w500/" + responseBody.poster_path)
                        .centerCrop()
                        .into(movieDetailImageView)

                    movieDetailTitle.text = responseBody.title
                    movieDetailReleaseDate.text = responseBody.release_date
                    movieDetailVoteAverage.text = responseBody.vote_average.toString() + "/10"
                    movieDetailOverview.text = responseBody.overview
                }
            }

            override fun onFailure(call: Call<MovieDetail?>, t: Throwable) {
                Log.d("MovieDetailActivityyy", t.message.toString())
            }
        })
    }

    private fun getSimilarMovies(context: Context, retrofitBuilder: ApiInterface, movieId: Int) {
        val retrofitSimilarMovies =
            retrofitBuilder.getSimilarMovies(movieId, "53ee22b69f31943882d306c2ba5fb1f9", "en-US", 1)

        retrofitSimilarMovies.enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(call: Call<MovieResponse?>, response: Response<MovieResponse?>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!.results
                    myAdapter = MovieAdapter(context, responseBody)
                    myAdapter.notifyDataSetChanged()
                    similarMoviesRecyclerView.adapter = myAdapter
                }
            }

            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                Log.d("MainActivity", t.message.toString())
            }
        })
    }


}