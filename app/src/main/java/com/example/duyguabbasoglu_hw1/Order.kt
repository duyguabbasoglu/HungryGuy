package com.example.duyguabbasoglu_hw1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeOrder(
    val recipeName: String,
    val imageRes: Int,
    val servings: Int,
    val note: String? = null
) : Parcelable
