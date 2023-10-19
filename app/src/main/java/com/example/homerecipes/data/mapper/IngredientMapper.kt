package com.example.homerecipes.data.mapper

import com.example.homerecipes.data.entity.IngredientEntity
import com.example.homerecipes.domain.model.IngredientDomain

fun IngredientDomain.toEntity() = IngredientEntity(
    id, name, idRecipe
)

fun IngredientEntity.toDomain() = IngredientDomain(
    id, name, idRecipe
)