package com.example.homerecipes.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.homerecipes.data.entity.RecipeEntity

@Dao
interface RecipeDAO {
    @Query("SELECT * FROM recipe")
    fun getAll(): List<RecipeEntity>

    @Insert
    fun insert(recipe: RecipeEntity)
}