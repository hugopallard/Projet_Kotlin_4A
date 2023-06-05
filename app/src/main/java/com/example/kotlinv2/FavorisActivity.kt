package com.example.kotlinv2

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin.model.Movie
import com.example.kotlinv2.fragments.BASE_URL
import com.example.kotlinv2.model.MovieDetail
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class FavorisActivity:AppCompatActivity() {

    private lateinit var myAdapter: MovieAdapter
    private lateinit var favorisAdapter: FavorisAdapter
    private lateinit var favorisMovieContainer: LinearLayout
    private lateinit var favorisMoviesRecyclerView: RecyclerView
    private var movieId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.favoris_view)
        this.window.decorView.setBackgroundColor(Color.BLACK)
        favorisMovieContainer = findViewById(R.id.favorisMoviesContainer)



        val movieList : ArrayList<Movie>
        val movieIdList = recupererliste()
        movieIdList.forEach{movieid ->
            val retrofitBuilder =
                Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build().create(ApiInterface::class.java)
            getMovieBasedOnId(this, retrofitBuilder, movieid)
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
//                    val movieDetailImageView = findViewById<ImageView>(R.id.movieDetailImageView)
//                    val movieDetailTitle = findViewById<TextView>(R.id.movieDetailTitle)
//                    val movieDetailReleaseDate = findViewById<TextView>(R.id.movieDetailReleaseDate)
//                    val movieDetailVoteAverage = findViewById<TextView>(R.id.movieDetailVoteAverage)
//                    val movieDetailOverview = findViewById<TextView>(R.id.movieDetailOverview)
//
//
//
//
//
//
//                    Glide.with(context)
//                        .load("https://image.tmdb.org/t/p/w500/" + responseBody.poster_path)
//                        .centerCrop()
//                        .into(movieDetailImageView)
//
//                    movieDetailTitle.text = responseBody.title
//                    movieDetailReleaseDate.text = responseBody.release_date
//                    movieDetailVoteAverage.text = responseBody.vote_average.toString() + "/10"
//                    movieDetailOverview.text = responseBody.overview
                    // creer un adapterqqui renvoie un moviedetails
                   // myAdapter = MovieAdapter(context, responseBody)
//                    myAdapter.notifyDataSetChanged()
//                    favorisMoviesRecyclerView.adapter = myAdapter
                }
            }

            override fun onFailure(call: Call<MovieDetail?>, t: Throwable) {
                Log.d("MovieDetailActivityyy", t.message.toString())
            }
        })
    }

    fun recupererliste():ArrayList<Int>{
        val movieIdList = ArrayList<Int>()
        val file = File("/data/user/0/com.example.kotlinv2/files/Favoris/listeFavoris.txt")
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
}