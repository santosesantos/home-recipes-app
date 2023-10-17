package com.example.homerecipes.domain.usecase

import com.example.homerecipes.domain.model.RecipeDomain
import com.example.homerecipes.domain.repository.RecipeRepository

class InsertRecipeUseCase constructor(
    private val repository: RecipeRepository
){
    suspend operator fun invoke(recipe: RecipeDomain) = repository.insert(recipe)
}