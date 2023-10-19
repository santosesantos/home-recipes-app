package com.example.homerecipes.data.entity

import androidx.room.Embedded
import androidx.room.Relation

typealias FullRecipeEntity = FullRecipe

data class FullRecipe(
    @Embedded val recipe: RecipeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "idRecipe"
    )
    val ingredient: List<IngredientEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "idRecipe"
    )
    val prepareMode: List<PrepareModeEntity>
)