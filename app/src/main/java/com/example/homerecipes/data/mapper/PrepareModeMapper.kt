package com.example.homerecipes.data.mapper

import com.example.homerecipes.data.entity.PrepareModeEntity
import com.example.homerecipes.domain.model.PrepareModeDomain

fun PrepareModeDomain.toEntity() = PrepareModeEntity(
    id, name, idRecipe
)

fun PrepareModeEntity.toDomain() = PrepareModeDomain(
    id, name, idRecipe
)