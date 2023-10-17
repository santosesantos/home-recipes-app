package com.example.homerecipes.domain.repository

import com.example.homerecipes.domain.model.RecipeDomain

interface RecipeRepository {
    suspend fun getAll(): List<RecipeDomain>
    suspend fun insert(recipe: RecipeDomain)
}