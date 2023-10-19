package com.example.homerecipes.domain.repository

import com.example.homerecipes.domain.model.FullRecipeDomain
import com.example.homerecipes.domain.model.IngredientDomain
import com.example.homerecipes.domain.model.PrepareModeDomain
import com.example.homerecipes.domain.model.RecipeDomain

interface RecipeRepository {
    suspend fun getAll(): List<RecipeDomain>
    suspend fun insert(recipe: RecipeDomain)
    suspend fun insert(ingredient: IngredientDomain)
    suspend fun insert(prepareMode: PrepareModeDomain)
    suspend fun getFullRecipe(recipeId: Int): FullRecipeDomain
    suspend fun updateIngredient(ingredient: IngredientDomain)
    suspend fun updatePrepareMode(prepareMode: PrepareModeDomain)
    suspend fun deleteIngredient(ingredient: IngredientDomain)
    suspend fun deletePrepareMode(prepareMode: PrepareModeDomain)
}