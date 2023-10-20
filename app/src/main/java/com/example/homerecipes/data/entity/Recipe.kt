package com.example.homerecipes.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

typealias RecipeEntity = Recipe

@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "prepareTime") val prepareTime: String
)