package com.example.projet_kotlin.model

class ProductionCompany(
    var id: Int,
    var logo_path: String? = null,
    var name: String,
    var origin_country: String
) {
    override fun toString(): String {
        return name
    }
}