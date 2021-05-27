package com.example.composerecipeapp.presentation.ui.recipelist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composerecipeapp.domain.model.Recipe
import com.example.composerecipeapp.repository.RecipeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class RecipeListViewModel @Inject constructor(
    val repository: RecipeRepository,
    @Named("auth_named") val token: String
) : ViewModel() {

     val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())

    init {
        viewModelScope.launch {
            val result = repository.search(
                token, 1, "carrot"
            )
            recipes.value = result
        }
    }
}