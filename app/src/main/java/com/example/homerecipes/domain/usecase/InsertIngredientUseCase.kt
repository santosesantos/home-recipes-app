package com.example.homerecipes.domain.usecase

import com.example.homerecipes.domain.model.IngredientDomain
import com.example.homerecipes.domain.repository.RecipeRepository

class InsertIngredientUseCase(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(ingredient: IngredientDomain) = repository.insert(ingredient)
}