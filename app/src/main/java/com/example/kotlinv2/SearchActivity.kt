package com.example.kotlinv2

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.model.Movie
import com.example.kotlin.model.MovieResponse
import com.example.kotlinv2.fragments.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private lateinit var myAdapter: SearchMovieAdapter
    private var searchQuery: String = ""
    private lateinit var moviesSearchResults: List<Movie>
    private lateinit var searchMoviesRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val extras = intent.extras
        if (extras != null) {

            searchMoviesRecyclerView = findViewById(R.id.searchMoviesRecyclerView)
            searchMoviesRecyclerView.setHasFixedSize(true)
            searchMoviesRecyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

            searchQuery = extras.getString("query")!!
            Log.e("Search query: ", searchQuery)
            val retrofitBuilder =
                Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build().create(ApiInterface::class.java)
            getMoviesBySearch(this, retrofitBuilder,searchQuery)

            val searchResultTextView = findViewById<TextView>(R.id.searchResultTextView)
            searchResultTextView.text = "Résultats pour: '" + searchQuery + "'"

        } else {
            Log.d("MovieDetailsActivity", "Error, please give a movie ID")
        }
    }

    private fun getMoviesBySearch(context: Context, retrofitBuilder: ApiInterface, query: String) {
        val retrofitPopularMovies =
            retrofitBuilder.getMoviesBySearch("53ee22b69f31943882d306c2ba5fb1f9", "en-US", query)

        retrofitPopularMovies.enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>
            ) {
                Log.d("Response: ", response.body().toString())
                if (response.isSuccessful) {
                    val responseBody = response.body()!!.results
                    moviesSearchResults = responseBody
                    Log.d("Result of the search: ", moviesSearchResults.toString())
                    myAdapter = SearchMovieAdapter(context, responseBody)
                    myAdapter.notifyDataSetChanged()
                    searchMoviesRecyclerView.adapter = myAdapter
                }
            }

            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                Log.d("MainActivity", t.message.toString())
            }
        })
    }
}