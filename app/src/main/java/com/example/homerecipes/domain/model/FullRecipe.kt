package com.example.homerecipes.domain.model

typealias FullRecipeDomain = FullRecipe

data class FullRecipe(
    val recipe: RecipeDomain,
    val ingredient: List<IngredientDomain>,
    val prepareMode: List<PrepareModeDomain>
)