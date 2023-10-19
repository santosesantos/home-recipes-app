package com.example.homerecipes.data.repository

import com.example.homerecipes.data.dao.RecipeDAO
import com.example.homerecipes.data.mapper.toDomain
import com.example.homerecipes.data.mapper.toEntity
import com.example.homerecipes.domain.model.FullRecipeDomain
import com.example.homerecipes.domain.model.IngredientDomain
import com.example.homerecipes.domain.model.PrepareModeDomain
import com.example.homerecipes.domain.model.RecipeDomain
import com.example.homerecipes.domain.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepositoryImpl(private val dao: RecipeDAO) : RecipeRepository {
    override suspend fun getAll(): List<RecipeDomain> = withContext(Dispatchers.IO) {
        dao.getAll().map {
            it.toDomain()
        }
    }

    override suspend fun insert(recipe: RecipeDomain) = withContext(Dispatchers.IO) {
        dao.insert(recipe.toEntity())
    }

    override suspend fun insert(ingredient: IngredientDomain) = withContext(Dispatchers.IO) {
        dao.insert(ingredient.toEntity())
    }

    override suspend fun insert(prepareMode: PrepareModeDomain) = withContext(Dispatchers.IO) {
        dao.insert(prepareMode.toEntity())
    }

    override suspend fun getFullRecipe(recipeId: Int): FullRecipeDomain =
        withContext(Dispatchers.IO) {
            dao.getFullRecipe(recipeId).toDomain()
        }

    override suspend fun updateIngredient(ingredient: IngredientDomain) = withContext(Dispatchers.IO) {
        dao.updateIngredient(ingredient.toEntity())
    }

    override suspend fun updatePrepareMode(prepareMode: PrepareModeDomain) = withContext(Dispatchers.IO) {
        dao.updatePrepareMode(prepareMode.toEntity())
    }

    override suspend fun deleteIngredient(ingredient: IngredientDomain) = withContext(Dispatchers.IO) {
        dao.deleteIngredient(ingredient.toEntity())
    }

    override suspend fun deletePrepareMode(prepareMode: PrepareModeDomain) = withContext(Dispatchers.IO) {
        dao.deletePrepareMode(prepareMode.toEntity())
    }
}