package com.example.composerecipeapp.presentation.components.util

sealed class RecipeListEvent {

    object NewSearchEvent : RecipeListEvent()
    object NewPageEvent : RecipeListEvent()

    // restore after process death
    object RestoreStateEvent : RecipeListEvent()
}
