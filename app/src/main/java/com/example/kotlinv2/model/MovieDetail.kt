package com.example.kotlinv2.model

import com.example.projet_kotlin.model.Genre
import com.example.projet_kotlin.model.ProductionCompany
import com.example.projet_kotlin.model.ProductionCountry
import com.example.projet_kotlin.model.SpokenLanguage

data class MovieDetail(
    var adult: Boolean,
    var backdrop_path: String? = null,
    var belongs_to_collection: MovieCollection? = null,
    var budget: Int,
    var genres: List<Genre>,
    var homepage: String? = null,
    var id: Int,
    var imdb_id: String? = null,
    var original_language: String,
    var original_title: String,
    var overview: String? = null,
    var popularity: Float,
    var poster_path: String? = null,
    var production_companies: List<ProductionCompany>,
    var production_countries: List<ProductionCountry>,
    var release_date: String,
    var revenue: Int,
    var runtime: Int,
    var spoken_languages: ArrayList<SpokenLanguage>,
    var status: String,
    var tagline: String? = null,
    var title: String,
    var video: Boolean,
    var vote_average: Float,
    var vote_count: Int,
) {
    override fun toString(): String {
        return "MovieDetail(id=$id, overview=$overview, release_date='$release_date', title='$title', vote_average=$vote_average)"
    }
}