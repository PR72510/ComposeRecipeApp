package com.example.composerecipeapp.network.model

import com.example.composerecipeapp.domain.model.Recipe
import com.example.composerecipeapp.domain.util.EntityMapper

class RecipeNetworkMapper : EntityMapper<RecipeNetworkEntity, Recipe> {

    override fun toEntity(model: Recipe): RecipeNetworkEntity {
        return RecipeNetworkEntity(
            model.pk,
            model.title,
            model.publisher,
            model.featuredImage,
            model.rating,
            model.sourceUrl,
            model.description,
            model.cookingInstructions,
            model.ingredients,
            model.dateAdded,
            model.dateUpdated
        )
    }

    override fun toDomainModel(entity: RecipeNetworkEntity): Recipe {
        return Recipe(
            entity.pk,
            entity.title,
            entity.publisher,
            entity.featuredImage,
            entity.rating,
            entity.sourceUrl,
            entity.description,
            entity.cookingInstructions,
            entity.ingredients.orEmpty(),
            entity.dateAdded,
            entity.dateUpdated
        )
    }

    fun toDomainList(initial: List<RecipeNetworkEntity>): List<Recipe> {
        return initial.map { toDomainModel(it) }
    }
}