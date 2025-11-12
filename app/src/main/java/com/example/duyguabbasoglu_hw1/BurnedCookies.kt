package com.example.duyguabbasoglu_hw1.model
import com.example.duyguabbasoglu_hw1.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class BurnedCookies(
    override val title: String = "Burned Chocolate Chip Cookies",
    override val imageRes: Int = R.drawable.burnt_cookie
) : RecipeBase {
    override fun ingredientsFor(servings: Int) = listOf(
        "${servings*1} cup flour",
        "${servings*0.5} cup sugar",
        "${servings} egg",
        "${servings*0.5} cup chocolate chips"
    )
}
