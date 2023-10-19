package com.example.homerecipes.domain.usecase

import com.example.homerecipes.domain.model.PrepareModeDomain
import com.example.homerecipes.domain.repository.RecipeRepository

class DeletePrepareModeUseCase(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(prepareModeDomain: PrepareModeDomain) = repository.deletePrepareMode(prepareModeDomain)
}