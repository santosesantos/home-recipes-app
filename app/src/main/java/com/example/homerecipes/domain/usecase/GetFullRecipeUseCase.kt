package com.example.homerecipes.domain.usecase

import com.example.homerecipes.domain.repository.RecipeRepository

class GetFullRecipeUseCase(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(recipeId: Int) = repository.getFullRecipe(recipeId)
}