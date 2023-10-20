package com.example.homerecipes.presentation.detail.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.homerecipes.data.db
import com.example.homerecipes.data.repository.RecipeRepositoryImpl
import com.example.homerecipes.domain.model.FullRecipeDomain
import com.example.homerecipes.domain.model.IngredientDomain
import com.example.homerecipes.domain.model.PrepareModeDomain
import com.example.homerecipes.domain.usecase.DeleteIngredientUseCase
import com.example.homerecipes.domain.usecase.DeletePrepareModeUseCase
import com.example.homerecipes.domain.usecase.GetFullRecipeUseCase
import com.example.homerecipes.domain.usecase.InsertIngredientUseCase
import com.example.homerecipes.domain.usecase.InsertPrepareModeUseCase
import com.example.homerecipes.domain.usecase.UpdateIngredientUseCase
import com.example.homerecipes.domain.usecase.UpdatePrepareModeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(
    private val idRecipe: Int,
    private val getFullRecipeUseCase: GetFullRecipeUseCase,
    private val insertIngredientUseCase: InsertIngredientUseCase,
    private val insertPrepareModeUseCase: InsertPrepareModeUseCase,
    private val updateIngredientUseCase: UpdateIngredientUseCase,
    private val updatePrepareModeUseCase: UpdatePrepareModeUseCase,
    private val deleteIngredientUseCase: DeleteIngredientUseCase,
    private val deletePrepareModeUseCase: DeletePrepareModeUseCase
): ViewModel() {
    private val _state = MutableSharedFlow<DetailState>()
    val state :SharedFlow<DetailState> = _state

    init {
        getFullRecipe()
    }

    private fun getFullRecipe() = viewModelScope.launch {
        getFullRecipeUseCase(idRecipe)
            .flowOn(Dispatchers.Main)
            .onStart {
                _state.emit(DetailState.Loading)
            }.catch {
                _state.emit(DetailState.Error(it.message.toString()))
            }.collect { fullRecipe ->
                _state.emit(DetailState.Success(fullRecipe))
            }
    }

    /*val state: LiveData<DetailState> = liveData {
        emit(DetailState.Loading)

        val state = try {
            val fullRecipe = getFullRecipeUseCase(idRecipe)
            DetailState.Success(fullRecipe)
        } catch (ex: Exception) {
            Log.e("Error", ex.message.toString())
            DetailState.Error(ex.message.toString())
        }

        emit(state)
    }*/

    fun insertIngredient(idRecipe: Int, ingredientName: String) = viewModelScope.launch {
        insertIngredientUseCase(IngredientDomain(idRecipe = idRecipe, name = ingredientName))
    }

    fun updateIngredient(idRecipe: Int, ingredientName: String, ingredientId: Int) = viewModelScope.launch {
        updateIngredientUseCase(IngredientDomain(
            id = ingredientId,
            idRecipe = idRecipe,
            name = ingredientName
        ))
    }

    fun insertPrepareMode(idRecipe: Int, prepareModeName: String) = viewModelScope.launch {
        insertPrepareModeUseCase(PrepareModeDomain(idRecipe = idRecipe, name = prepareModeName))
    }
    fun updatePrepareMode(idRecipe: Int, prepareModeName: String, prepareModeId: Int) = viewModelScope.launch {
        updatePrepareModeUseCase(PrepareModeDomain(
            id = prepareModeId,
            idRecipe = idRecipe,
            name = prepareModeName
        ))
    }

    fun deleteIngredient(idRecipe: Int, ingredientName: String, ingredientId: Int) = viewModelScope.launch {
        deleteIngredientUseCase(IngredientDomain(
            idRecipe = idRecipe,
            id = ingredientId,
            name = ingredientName
        ))
    }

    fun deletePrepareMode(idRecipe: Int, prepareModeName: String, prepareModeId: Int) = viewModelScope.launch {
        deletePrepareModeUseCase(PrepareModeDomain(
            idRecipe = idRecipe,
            id = prepareModeId,
            name = prepareModeName
        ))
    }

    class Factory(private val idRecipe: Int): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
            val repository = RecipeRepositoryImpl(application.db.recipeDAO())
            return DetailViewModel(
                idRecipe = idRecipe,
                getFullRecipeUseCase = GetFullRecipeUseCase(repository),
                insertIngredientUseCase = InsertIngredientUseCase(repository),
                insertPrepareModeUseCase = InsertPrepareModeUseCase(repository),
                updateIngredientUseCase = UpdateIngredientUseCase(repository),
                updatePrepareModeUseCase = UpdatePrepareModeUseCase(repository),
                deleteIngredientUseCase = DeleteIngredientUseCase(repository),
                deletePrepareModeUseCase = DeletePrepareModeUseCase(repository)
            ) as T
        }
    }
}