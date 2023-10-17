package com.example.homerecipes.data.mapper

import com.example.homerecipes.data.entity.RecipeEntity
import com.example.homerecipes.domain.model.RecipeDomain

fun RecipeDomain.toEntity() = RecipeEntity(
    id = id,
    name = name
)

fun RecipeEntity.toDomain() = RecipeDomain(
    id = id,
    name = name
)