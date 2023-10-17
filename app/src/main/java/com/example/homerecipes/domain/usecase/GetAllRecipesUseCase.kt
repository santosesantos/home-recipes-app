package com.example.homerecipes.domain.usecase

import com.example.homerecipes.domain.repository.RecipeRepository

class GetAllRecipesUseCase constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke() = repository.getAll()
}