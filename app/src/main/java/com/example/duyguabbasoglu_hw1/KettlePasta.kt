package com.example.duyguabbasoglu_hw1.model
import com.example.duyguabbasoglu_hw1.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class KettlePasta(
    override val title: String = "Kettle Tuna Pasta",
    override val imageRes: Int = R.drawable.kettle_pasta
) : RecipeBase {
    override fun ingredientsFor(servings: Int) = listOf(
        "${servings*80} g pasta",
        "${servings*1} can tuna",
        "${servings*2} tbsp mayo",
        "salt & pepper"
    )
}
