package com.example.projet_kotlin.model

class SpokenLanguage(
    var iso_639_1: String,
    var name: String
) {
    override fun toString(): String {
        return name
    }
}