package com.example.composerecipeapp.repository

import com.example.composerecipeapp.domain.model.Recipe
import com.example.composerecipeapp.network.RecipeService
import com.example.composerecipeapp.network.model.RecipeDtoMapper
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    val service: RecipeService,
    val mapper: RecipeDtoMapper
) : RecipeRepository {

    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        val result = service.search(token, page, query)
        return mapper.toDomainList(result.recipes)
    }

    override suspend fun getRecipe(token: String, id: Int): Recipe {
        val result = service.get(token, id)
        return mapper.mapToDomainModel(result)
    }
}