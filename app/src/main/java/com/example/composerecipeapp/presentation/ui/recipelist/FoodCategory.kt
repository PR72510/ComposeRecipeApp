package com.example.composerecipeapp.presentation.ui.recipelist

import com.example.composerecipeapp.presentation.ui.recipelist.FoodCategory.*

enum class FoodCategory(val value: String) {
    CHICKEN("Chicken"),
    SOUP("Soup"),
    DESSERT("Dessert"),
    VEGETARIAN("Vegetarian"),
    MILK("Milk"),
    VEGAN("Vegan"),
    PIZZA("Pizza"),
    DONUT("Donut"),
    Cookies("Cookies")
}

fun getAllFoodCategories(): List<FoodCategory> {
    return listOf(CHICKEN, SOUP, DESSERT, VEGETARIAN, MILK, VEGAN, PIZZA, DONUT, Cookies)
}

fun getFoodCategory(value: String): FoodCategory? {
    val map = FoodCategory.values().associateBy(FoodCategory::value)
    return map[value]
}