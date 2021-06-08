package com.example.composerecipeapp.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composerecipeapp.domain.model.Recipe
import com.example.composerecipeapp.presentation.ui.recipe.RecipeEvent.*
import com.example.composerecipeapp.presentation.ui.recipelist.STATE_KEY_PAGE
import com.example.composerecipeapp.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Named

const val STATE_KEY_RECIPE = "state.key.recipeId"

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private @Named("auth_token") val token: String,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val loading = mutableStateOf(false)

    init {
        savedStateHandle.get<Int>(STATE_KEY_RECIPE)
            ?.let { recipeId -> onTriggerEvent(GetRecipeEvent(recipeId)) }
    }

    fun onTriggerEvent(event: RecipeEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is GetRecipeEvent -> {
                        if (recipe.value == null) {
                            getRecipe(event.id)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("MyTag", "onTriggerEvent: Exception $e, ${e.cause}")
            }
        }
    }

    private suspend fun getRecipe(id: Int) {
        loading.value = true

        val recipe = repository.getRecipe(token, id)
        this.recipe.value = recipe

        savedStateHandle.set(STATE_KEY_RECIPE, recipe.pk)
        loading.value = false
    }
}