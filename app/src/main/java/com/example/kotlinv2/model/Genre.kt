package com.example.projet_kotlin.model

class Genre(
    var id: Int,
    var name: String
) {
    override fun toString(): String {
        return name
    }
}