package com.namesurname.hw1.model
import com.example.duyguabbasoglu_hw1.model.RecipeBase
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    val recipe: RecipeBase,
    val servings: Int
) : android.os.Parcelable