package com.example.homerecipes.domain.usecase

import com.example.homerecipes.domain.model.PrepareModeDomain
import com.example.homerecipes.domain.repository.RecipeRepository

class InsertPrepareModeUseCase(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(prepareMode: PrepareModeDomain) = repository.insert(prepareMode)
}