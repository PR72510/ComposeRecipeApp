package com.example.composerecipeapp.presentation.ui.recipelist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composerecipeapp.domain.model.Recipe
import com.example.composerecipeapp.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    val repository: RecipeRepository,
    @Named("auth_token") val token: String
) : ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

    var categoryScrollPosition = 0
    init {
        newSearch()
    }

    fun newSearch() {
        viewModelScope.launch {
            val result = repository.search(
                token, 1, query.value
            )
            recipes.value = result
        }
    }

    fun onQueryChanged(newQuery: String) {
        query.value = newQuery
    }

    fun onSelectedCategoryChanged(category: String){
        selectedCategory.value = getFoodCategory(category)
        onQueryChanged(category)
    }

    fun changeCategoryScrollPosition(position: Int){
        categoryScrollPosition = position
    }
}