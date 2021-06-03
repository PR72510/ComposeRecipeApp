package com.example.composerecipeapp.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.composerecipeapp.presentation.ui.recipelist.FoodCategory
import com.example.composerecipeapp.presentation.ui.recipelist.getAllFoodCategories
import kotlinx.coroutines.launch

@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onNewSearch: () -> Unit,
    categoryScrollPosition: Int,
    selectedCategory: FoodCategory?,
    onSelectedCategoryChanged: (String) -> Unit,
    changeScrollPosition: (Int) -> Unit,
    onToggleTheme: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.surface,
        elevation = 8.dp
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(8.dp),
                    value = query,
                    onValueChange = { newValue ->
                        onQueryChanged(newValue)
                    },
                    label = {
                        Text(text = "Search")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    leadingIcon = {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    },
                    keyboardActions = KeyboardActions(onSearch = {
                        onNewSearch()
                        focusManager.clearFocus()       // hide softkeyboard
                    }),
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.onSurface
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface
                    )

                )
                IconButton(onClick = { onToggleTheme() },
                modifier = Modifier.padding(top = 8.dp)) {
                    Icon(Icons.Filled.MoreVert, contentDescription = null)
                }
            }
            val scrollState = rememberScrollState()
            val scope = rememberCoroutineScope()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState),
            ) {
                scope.launch {
                    scrollState.scrollTo(categoryScrollPosition)
                }
                for (category in getAllFoodCategories()) {
                    FoodCategoryChip(
                        category = category.value,
                        isSelected = selectedCategory == category,
                        onSelectedCategoryChanged = {
                            onSelectedCategoryChanged(it)
                            changeScrollPosition(scrollState.value)
                        },
                        onExecuteSearch = { onNewSearch() }
                    )
                }
            }
        }
    }
}