package com.example.homerecipes.domain.usecase

import com.example.homerecipes.domain.model.IngredientDomain
import com.example.homerecipes.domain.repository.RecipeRepository

class DeleteIngredientUseCase(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(ingredientDomain: IngredientDomain) = repository.deleteIngredient(ingredientDomain)
}