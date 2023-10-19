package com.example.homerecipes.data.mapper

import com.example.homerecipes.data.entity.FullRecipeEntity
import com.example.homerecipes.domain.model.FullRecipeDomain

fun FullRecipeDomain.toEntity() = FullRecipeEntity(
    recipe = recipe.toEntity(),
    ingredient = ingredient.map { it.toEntity() },
    prepareMode = prepareMode.map { it.toEntity() }
)

fun FullRecipeEntity.toDomain() = FullRecipeDomain(
    recipe = recipe.toDomain(),
    ingredient = ingredient.map { it.toDomain() },
    prepareMode = prepareMode.map { it.toDomain() }
)