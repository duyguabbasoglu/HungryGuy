package com.example.duyguabbasoglu_hw1.model
import com.example.duyguabbasoglu_hw1.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class ToasterQuesadilla(
    override val title: String = "Toaster Quesadilla",
    override val imageRes: Int = R.drawable.toaster_tost
) : RecipeBase {
    override fun ingredientsFor(servings: Int) = listOf(
        "${servings*2} tortilla",
        "${servings*50} g cheese",
        "corn/beans (optional)"
    )
}