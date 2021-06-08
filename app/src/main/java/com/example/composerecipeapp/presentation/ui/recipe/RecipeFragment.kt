package com.example.composerecipeapp.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.composerecipeapp.BaseApplication
import com.example.composerecipeapp.presentation.components.CircularIndeterminateProgressBar
import com.example.composerecipeapp.presentation.components.RecipeView
import com.example.composerecipeapp.presentation.components.util.SnackbarController
import com.example.composerecipeapp.presentation.theme.AppTheme
import com.example.composerecipeapp.presentation.ui.recipe.RecipeEvent.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    @Inject
    lateinit var snackbarController: SnackbarController
    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipeId")?.let { recipeId ->
            viewModel.onTriggerEvent(GetRecipeEvent(recipeId))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val loading = viewModel.loading.value
                val recipe = viewModel.recipe.value

                val scaffoldState = rememberScaffoldState()

                AppTheme(darkTheme = application.isDark.value) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (loading && recipe == null) {
                                Text(text = "Loading...")
                            } else {
                                recipe?.let {
                                    if (it.pk == 1) {
                                        snackbarController.showSnackbar(
                                            scaffoldState,
                                            "Error occured",
                                            "Ok"
                                        )
                                    } else {
                                        RecipeView(recipe = it)
                                    }
                                }
                            }
                        }
                        CircularIndeterminateProgressBar(
                            isVisible = loading,
                            verticalBias = 0.3f
                        )
                    }
                }
            }
        }
    }
}