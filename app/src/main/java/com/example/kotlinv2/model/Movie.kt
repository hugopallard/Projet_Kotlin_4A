package com.example.kotlin.model

data class Movie(
    var poster_path: String? = "",
    var adult: Boolean? = false,
    var overview: String? = "",
    var release_date: String? = "",
    var genre_ids: ArrayList<Int> = arrayListOf(),
    var id: Int? = 0,
    var original_title: String? = "",
    var original_language: String? = "",
    var title: String? = "",
    var backdrop_path: String? = "",
    var popularity: Double? = 0.0,
    var vote_count: Int? = 0,
    var video: Boolean? = false,
    var vote_average: Double? = 0.0
) {
    override fun toString(): String {
        return "PopularMovie(poster_path=$poster_path, adult=$adult, overview=$overview, release_date=$release_date, genre_ids=$genre_ids, id=$id, original_title=$original_title, original_language=$original_language, title=$title, backdrop_path=$backdrop_path, popularity=$popularity, vote_count=$vote_count, video=$video, vote_average=$vote_average)"
    }
}


