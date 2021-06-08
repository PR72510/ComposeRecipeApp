package com.example.composerecipeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.composerecipeapp.domain.model.Recipe
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun RecipeView(
    recipe: Recipe
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        recipe.featuredImage?.let { url ->
            Image(
                painter = rememberCoilPainter(request = url),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
                ) {
                    recipe.title?.let {
                        Text(
                            text = it,
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .wrapContentWidth(Alignment.Start),
                            style = MaterialTheme.typography.h3
                        )
                    }
                    val rank = recipe.rating.toString()
                    Text(
                        text = rank,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.Start)
                            .align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.h5
                    )
                }
            }
            recipe.publisher?.let { publisher ->
                val updated = recipe.dateUpdated
                Text(
                    text = if (updated != null) {
                        "Updated $updated by $publisher"
                    } else {
                        "By $publisher"
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp, start = 8.dp),
                    style = MaterialTheme.typography.caption
                )
            }
            for(ingredients in recipe.ingredients){
                Text(
                    text = ingredients,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp, start = 8.dp),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}