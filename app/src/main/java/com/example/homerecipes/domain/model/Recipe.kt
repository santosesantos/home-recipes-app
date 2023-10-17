package com.example.homerecipes.domain.model

typealias RecipeDomain = Recipe

data class Recipe(
    val id: Int = 0,
    val name: String
)
