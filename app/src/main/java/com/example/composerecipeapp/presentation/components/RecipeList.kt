package com.example.composerecipeapp.presentation.components

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.composerecipeapp.R
import com.example.composerecipeapp.domain.model.Recipe
import com.example.composerecipeapp.presentation.components.util.RecipeListEvent
import com.example.composerecipeapp.presentation.components.util.SnackbarController
import com.example.composerecipeapp.presentation.ui.recipelist.PAGE_SIZE
import kotlinx.coroutines.launch

@Composable
fun RecipeList(
    isLoading: Boolean,
    recipes: List<Recipe>,
    onChangeRecipeScrollPosition: (Int) -> Unit,
    onNextPage: (RecipeListEvent) -> Unit,
    page: Int,
    scaffoldState: ScaffoldState,
    snackbarController: SnackbarController,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
    ) {
        LazyColumn {
            itemsIndexed(
                items = recipes
            ) { index, recipe ->
                onChangeRecipeScrollPosition(index)
                if ((index + 1) >= (page * PAGE_SIZE) && !isLoading) {
                    onNextPage(RecipeListEvent.NewPageEvent)
                }
                RecipeCard(
                    recipe = recipe,
                    onClick = {
                        if (recipe.pk != null) {
                            val bundle = Bundle()
                            bundle.putInt("recipeId", recipe.pk)
                            navController.navigate(
                                R.id.action_recipeListFragment_to_recipeFragment,
                                bundle
                            )
                        } else {
                            snackbarController.getScope().launch {
                                snackbarController.showSnackbar(
                                    scaffoldState = scaffoldState,
                                    "Recipe Error", "Ok"
                                )
                            }
                        }
                    })
            }
        }
        CircularIndeterminateProgressBar(isVisible = isLoading)
        DefaultSnackbar(
            snackbarHostState = scaffoldState.snackbarHostState,
            onDismiss = {
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}