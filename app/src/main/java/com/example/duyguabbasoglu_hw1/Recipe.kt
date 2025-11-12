package com.example.duyguabbasoglu_hw1.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id: String,
    val title: String,
    val imageRes: Int,
    val baseServings: Int = 1
) : Parcelable
