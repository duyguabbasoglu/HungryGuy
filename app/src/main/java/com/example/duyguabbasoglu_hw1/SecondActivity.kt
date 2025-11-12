package com.example.duyguabbasoglu_hw1

import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.ObservableField
import com.example.duyguabbasoglu_hw1.databinding.ActivitySecondBinding
import kotlin.math.max

class SecondActivity : AppCompatActivity() {
    lateinit var bindingSec : ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingSec = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(bindingSec.root)

        val title = ObservableField(recipe.title)
        val imageRes = ObservableField(recipe.imageRes)
        val servings = ObservableField(servingsInit)
        val ingredientsText = ObservableField(recipe.ingredientsFor(servingsInit).joinToString("\n"))

        fun update(recipe: RecipeBase, s: Int) {
            servings.set(s)
            ingredientsText.set(recipe.ingredientsFor(s).joinToString("\n"))
        }

    }
}