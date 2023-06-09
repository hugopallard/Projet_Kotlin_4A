package com.example.kotlinv2.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.model.Movie
import com.example.kotlinv2.*
import com.example.kotlinv2.adapter.MovieAdapter
import com.example.kotlinv2.adapter.MovieDetailAdapter
import com.example.kotlinv2.model.MovieDetail
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class FavoritesFragment : Fragment() {

    private lateinit var fragmentAdapter: MovieDetailAdapter
    private lateinit var favorisMoviesRecyclerView: RecyclerView
    private lateinit var listOfFavorites: ArrayList<Int>
    private lateinit var movieList: ArrayList<MovieDetail>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_favoris, container, false)
        val retrofitClient =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL)
                .build().create(ApiInterface::class.java)

        favorisMoviesRecyclerView = root.findViewById(R.id.favorisMoviesRecyclerView)

        listOfFavorites = arrayListOf()
        listOfFavorites = getListOfFavorites()

        Log.d("liste de favoris avec ID", listOfFavorites.toString())

        movieList = arrayListOf()
        movieList = getFavoritesBasedOnId(retrofitClient, listOfFavorites)

        Log.d("liste de films après call d'API", movieList.toString())
        fragmentAdapter = MovieDetailAdapter(root.context, movieList)
        fragmentAdapter.notifyDataSetChanged()
        favorisMoviesRecyclerView.adapter = fragmentAdapter

        return root
    }

    fun getListOfFavorites(): ArrayList<Int> {
        val movieIdList = ArrayList<Int>()
        val file = File("/data/user/0/com.example.kotlinv2/files/listeFavoris.txt")
        if (file.exists() && file.canRead()) {
            file.bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    val id = line.trim()
                    movieIdList.add(Integer.parseInt(id))
                }
            }
        } else {
            println("fichier non existant ou illisible")
        }
        return movieIdList
    }

    private fun getFavoritesBasedOnId(
        retrofitClient: ApiInterface,
        listOfFavorites: ArrayList<Int>
    ): ArrayList<MovieDetail> {
        val movieList: ArrayList<MovieDetail> = arrayListOf()
        runBlocking {
            for (favId in listOfFavorites) {
                val retrofitMovie: MovieDetail =
                    retrofitClient.getMovieDetailBasedOnId_Blocking(
                        favId,
                        "53ee22b69f31943882d306c2ba5fb1f9",
                        "en-US"
                    )
                movieList.add(retrofitMovie)
            }
        }
        return movieList
    }
}