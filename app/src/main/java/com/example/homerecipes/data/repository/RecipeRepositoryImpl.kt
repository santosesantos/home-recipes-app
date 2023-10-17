package com.example.homerecipes.data.repository

import com.example.homerecipes.data.dao.RecipeDAO
import com.example.homerecipes.data.mapper.toDomain
import com.example.homerecipes.data.mapper.toEntity
import com.example.homerecipes.domain.model.RecipeDomain
import com.example.homerecipes.domain.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepositoryImpl(private val dao: RecipeDAO): RecipeRepository {
    override suspend fun getAll(): List<RecipeDomain> = withContext(Dispatchers.IO) {
        dao.getAll().map {
            it.toDomain()
        }
    }

    override suspend fun insert(recipe: RecipeDomain) = withContext(Dispatchers.IO) {
        dao.insert(recipe.toEntity())
    }
}