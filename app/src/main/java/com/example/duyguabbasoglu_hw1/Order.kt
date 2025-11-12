package com.example.duyguabbasoglu_hw1.model
data class Order(
    val recipe: Recipe,
    val servings: Int,
    val note: String? = null
)