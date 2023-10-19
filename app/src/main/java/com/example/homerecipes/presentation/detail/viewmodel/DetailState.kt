package com.example.homerecipes.presentation.detail.viewmodel

import com.example.homerecipes.domain.model.FullRecipeDomain

sealed interface DetailState {
    object Loading : DetailState
    data class Success(val fullRecipe: FullRecipeDomain) : DetailState
    data class Error(val message: String) : DetailState
}