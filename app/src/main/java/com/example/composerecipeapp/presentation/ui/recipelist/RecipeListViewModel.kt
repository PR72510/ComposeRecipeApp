package com.example.composerecipeapp.presentation.ui.recipelist

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composerecipeapp.domain.model.Recipe
import com.example.composerecipeapp.presentation.components.util.RecipeListEvent
import com.example.composerecipeapp.presentation.components.util.RecipeListEvent.*
import com.example.composerecipeapp.repository.RecipeRepository
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE = 30

const val STATE_KEY_PAGE = "recipe.state.page.key"
const val STATE_KEY_QUERY = "recipe.state.query.key"
const val STATE_KEY_LIST_POSITION = "recipe.state.query.list_position"
const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query.selected_category"

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private @Named("auth_token") val token: String,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    val isLoading = mutableStateOf(false)

    val page = mutableStateOf(1)
    var recipeListScrollPosition = 0

    var categoryScrollPosition = 0

    init {
        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { page -> setPage(page) }
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { query -> setQuery(query) }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)
            ?.let { position -> setListScrollPosition(position) }
        savedStateHandle.get<FoodCategory>(STATE_KEY_SELECTED_CATEGORY)
            ?.let { category -> setSelectedCategory(category) }

        if (recipeListScrollPosition != 0)
            onTriggerEvent(RestoreStateEvent)
        else
            onTriggerEvent(NewSearchEvent)
    }

    fun onTriggerEvent(event: RecipeListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    NewSearchEvent -> newSearch()
                    NewPageEvent -> nextPage()
                    RestoreStateEvent -> restoreState()
                }
            } catch (e: Exception) {
                Log.e("MyTag", "launchJob exception: $e, ${e.cause}")
                e.printStackTrace()
            } finally {
                Log.d("MyTag", "launchJob: finally called")
            }
        }
    }

    private suspend fun restoreState() {
        isLoading.value = true
        val results = mutableListOf<Recipe>()
        for (p in 1..page.value) {
            val result = repository.search(
                token, p, query.value
            )
            results.addAll(result)
            if (p == page.value) {
                recipes.value = results
                isLoading.value = false
            }
        }
    }

    private suspend fun newSearch() {
        isLoading.value = true
        resetSearchState()
        val result = repository.search(
            token, 1, query.value
        )
        recipes.value = result
        isLoading.value = false

    }

    private fun resetSearchState() {
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if (selectedCategory.value?.value != query.value) {
            clearSelectedCategory()
        }
    }

    private fun clearSelectedCategory() {
        setSelectedCategory(null)
        selectedCategory.value = null
    }

    fun onQueryChanged(newQuery: String) {
        setQuery(newQuery)
    }

    fun onSelectedCategoryChanged(category: String) {
        setSelectedCategory(getFoodCategory(category))
        onQueryChanged(category)
    }

    fun changeCategoryScrollPosition(position: Int) {
        categoryScrollPosition = position
    }

    private fun incrementPage() {
        setPage(page.value + 1)
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        setListScrollPosition(position = position)
    }

    private fun appendRecipes(recipe: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipe)
        this.recipes.value = current
    }

    private suspend fun nextPage() {
        if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            isLoading.value = true
            incrementPage()

            if (page.value > 1) {
                val result = repository.search(
                    token, page.value, query.value
                )
                appendRecipes(result)
            }
            isLoading.value = false
        }
    }

    private fun setListScrollPosition(position: Int) {
        recipeListScrollPosition = position
        savedStateHandle.set(STATE_KEY_LIST_POSITION, position)
    }

    private fun setPage(page: Int) {
        this.page.value = page
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }

    private fun setSelectedCategory(category: FoodCategory?) {
        selectedCategory.value = category
        savedStateHandle.set(STATE_KEY_SELECTED_CATEGORY, category)
    }

    private fun setQuery(query: String) {
        this.query.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }
}