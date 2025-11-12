package com.example.duyguabbasoglu_hw1.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id: String,
    val base: RecipeBase,
    val baseServings: Int = 1
) : Parcelable
