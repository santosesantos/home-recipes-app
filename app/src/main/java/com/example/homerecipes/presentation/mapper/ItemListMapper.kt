package com.example.homerecipes.presentation.mapper

import com.example.homerecipes.domain.model.IngredientDomain
import com.example.homerecipes.domain.model.PrepareModeDomain
import com.example.homerecipes.presentation.model.ItemListModel

fun IngredientDomain.toModel() = ItemListModel(
    id = id,
    name = name
)

fun List<IngredientDomain>.toModelIngredient() = map {
    it.toModel()
}

fun PrepareModeDomain.toModel() = ItemListModel(
    id = id,
    name = name
)

fun List<PrepareModeDomain>.toModelPrepareMode() = map {
    it.toModel()
}