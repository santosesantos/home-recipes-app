package com.example.homerecipes.domain.usecase

import com.example.homerecipes.domain.model.PrepareModeDomain
import com.example.homerecipes.domain.repository.RecipeRepository

class UpdatePrepareModeUseCase(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(prepareModeDomain: PrepareModeDomain) = repository.updatePrepareMode(prepareModeDomain)
}