package com.example.kotlinv2.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.model.MovieResponse
import com.example.kotlinv2.*
import com.example.kotlinv2.adapter.MovieAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://api.themoviedb.org/3/"

class HomeFragment : Fragment() {

    private lateinit var fragmentAdapter: MovieAdapter
    private lateinit var popularMovieContainer: LinearLayout
    private lateinit var trendingMoviesContainer: LinearLayout
    private lateinit var popularMoviesRecyclerView: RecyclerView
    private lateinit var trendingMoviesRecyclerView: RecyclerView

    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        popularMovieContainer = root.findViewById(R.id.popularMoviesContainer)
        trendingMoviesContainer = root.findViewById(R.id.trendingMoviesContainer)

        popularMoviesRecyclerView = root.findViewById(R.id.popularMoviesRecyclerView)
        popularMoviesRecyclerView.setHasFixedSize(true)
        popularMoviesRecyclerView.layoutManager =
            LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)

        trendingMoviesRecyclerView = root.findViewById(R.id.trendingMoviesRecyclerView)
        trendingMoviesRecyclerView.setHasFixedSize(true)
        trendingMoviesRecyclerView.layoutManager =
            LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)

        searchView = root.findViewById(R.id.searchView)

        val retrofitBuilder =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL)
                .build().create(ApiInterface::class.java)

        if(!searchView.isFocused){
            getPopularMovies(root.context, retrofitBuilder)
            getTrendingMovies(root.context, retrofitBuilder)
        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                if(query != null){
                    val intent = Intent(root.context, SearchActivity::class.java)
                    intent.putExtra("query", query)
                    root.context.startActivity(intent)
                }
                if(query == null){
                    println("Cleared")
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        })

        return root
    }

    private fun getPopularMovies(context: Context, retrofitBuilder: ApiInterface) {
        val retrofitPopularMovies =
            retrofitBuilder.getPopularMovies("53ee22b69f31943882d306c2ba5fb1f9", "en-US", 2)

        retrofitPopularMovies.enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!.results
                    fragmentAdapter = MovieAdapter(context, responseBody)
                    fragmentAdapter.notifyDataSetChanged()
                    popularMoviesRecyclerView.adapter = fragmentAdapter
                }
            }

            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                Log.d("MainActivity", t.message.toString())
            }
        })
    }

    private fun getTrendingMovies(context: Context, retrofitBuilder: ApiInterface) {

        val retrofitTrendingMovies =
            retrofitBuilder.getTrendingMovies("week", "53ee22b69f31943882d306c2ba5fb1f9", "en-US")

        retrofitTrendingMovies.enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!.results
                    fragmentAdapter = MovieAdapter(context, responseBody)
                    fragmentAdapter.notifyDataSetChanged()
                    trendingMoviesRecyclerView.adapter = fragmentAdapter
                }
            }

            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                Log.d("MainActivity", t.message.toString())
            }
        })
    }
}