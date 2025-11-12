package com.example.duyguabbasoglu_hw1.model
import com.example.duyguabbasoglu_hw1.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class MugOmlette(
    override val title: String = "Microwave Mug Omlette",
    override val imageRes: Int = R.drawable.mug_omlette
) : RecipeBase {
    override fun ingredientsFor(servings: Int) = listOf(
        "${servings*2} egg",
        "${servings*2} tbsp milk",
        "cheese, salt"
    )
}