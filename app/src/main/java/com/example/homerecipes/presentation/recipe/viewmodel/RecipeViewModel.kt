package com.example.homerecipes.presentation.recipe.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.homerecipes.data.db
import com.example.homerecipes.data.repository.RecipeRepositoryImpl
import com.example.homerecipes.domain.model.RecipeDomain
import com.example.homerecipes.domain.usecase.GetAllRecipesUseCase
import com.example.homerecipes.domain.usecase.InsertRecipeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val getAllRecipesUseCase: GetAllRecipesUseCase,
    private val insertRecipeUseCase: InsertRecipeUseCase
): ViewModel() {
    private val _state = MutableSharedFlow<RecipeState>()
    val state: SharedFlow<RecipeState> = _state

    init {
        getAllRecipes()
    }

    private fun getAllRecipes() = viewModelScope.launch {
        getAllRecipesUseCase()
            .flowOn(Dispatchers.Main)
            .onStart {
                _state.emit(RecipeState.Loading)
            }.catch {
                _state.emit(RecipeState.Error(it.message.toString()))
            }.collect { recipes ->
                if (recipes.isEmpty())
                    _state.emit(RecipeState.Empty)
                else
                    _state.emit(RecipeState.Success(recipes))
            }
    }

    // This is the implementation of the "state-machine" for the Recipes DB interaction
    /*val state: LiveData<RecipeState> = liveData {
        emit(RecipeState.Loading)

        val state = try {
            val recipes = getAllRecipesUseCase()
            if (recipes.isEmpty()) {
                RecipeState.Empty
            } else {
                RecipeState.Success(recipes)
            }
        } catch (ex: Exception) {
            Log.e("Error", ex.message.toString())
            RecipeState.Error(ex.message.toString())
        }

        emit(state)
    }*/

    fun insert(name: String) = viewModelScope.launch {
        insertRecipeUseCase(RecipeDomain(name = name, prepareTime = "42 min"))
    }

    class Factory: ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
            val repository = RecipeRepositoryImpl(application.db.recipeDAO())
            return RecipeViewModel(
                getAllRecipesUseCase = GetAllRecipesUseCase(repository),
                insertRecipeUseCase = InsertRecipeUseCase(repository)
            ) as T
        }
    }
}