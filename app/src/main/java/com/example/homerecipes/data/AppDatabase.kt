package com.example.homerecipes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.homerecipes.data.dao.RecipeDAO
import com.example.homerecipes.data.entity.RecipeEntity

@Database(
    entities = [
        RecipeEntity::class
    ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun recipeDAO(): RecipeDAO
}