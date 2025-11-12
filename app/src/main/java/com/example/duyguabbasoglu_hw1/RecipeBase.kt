package com.example.duyguabbasoglu_hw1.model
import android.os.Parcelable
import androidx.annotation.DrawableRes

interface RecipeBase : Parcelable {
    val title: String
    @get:DrawableRes val imageRes: Int
    fun ingredientsFor(servings: Int): List<String>
}