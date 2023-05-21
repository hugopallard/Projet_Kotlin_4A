package com.example.projet_kotlin.model

class ProductionCountry(
    var iso_3166_1: String,
    var name: String
) {
    override fun toString(): String {
        return name
    }
}