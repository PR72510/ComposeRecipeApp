package com.example.composerecipeapp.network.model

import com.example.composerecipeapp.domain.model.Recipe
import com.example.composerecipeapp.domain.util.DomainMapper

class RecipeDtoMapper : DomainMapper<RecipeDto, Recipe> {

    override fun mapFromDomainModel(model: Recipe): RecipeDto {
        return RecipeDto(
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

    override fun mapToDomainModel(model: RecipeDto): Recipe {
        return Recipe(
            model.pk,
            model.title,
            model.publisher,
            model.featuredImage,
            model.rating,
            model.sourceUrl,
            model.description,
            model.cookingInstructions,
            model.ingredients.orEmpty(),
            model.dateAdded,
            model.dateUpdated
        )
    }

    fun toDomainList(initial: List<RecipeDto>): List<Recipe> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Recipe>): List<RecipeDto> {
        return initial.map { mapFromDomainModel(it) }
    }
}