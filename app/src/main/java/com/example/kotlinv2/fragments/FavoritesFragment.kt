package com.example.kotlinv2.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinv2.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FavoritesFragment : Fragment() {

    private lateinit var favorisAdapter: FavorisAdapter
    private lateinit var favorisMovieContainer: LinearLayout
    private lateinit var favorisMoviesRecyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_favoris, container, false)

        favorisMoviesRecyclerView = root.findViewById(R.id.favorisMoviesRecyclerView)



        val retrofitBuilder =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL)
                .build().create(ApiInterface::class.java)

//        favorisAdapter = FavorisAdapter(root.context, movieList )
//        favorisAdapter.notifyDataSetChanged()
//        favorisMoviesRecyclerView.adapter = favorisAdapter

        return root
    }


}