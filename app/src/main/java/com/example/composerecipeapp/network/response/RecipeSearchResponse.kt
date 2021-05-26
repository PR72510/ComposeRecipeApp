package com.example.composerecipeapp.network.response

import com.example.composerecipeapp.domain.model.Recipe
import com.google.gson.annotations.SerializedName

 data class RecipeSearchResponse(
    @SerializedName("count")
    var count: Int,

    @SerializedName("results")
    var recipes: List<Recipe>
)