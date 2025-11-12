package com.example.duyguabbasoglu_hw1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ctis487.lab.myapplication.LocaleHelper
import com.example.duyguabbasoglu_hw1.databinding.ActivitySecondBinding
import com.example.duyguabbasoglu_hw1.model.Recipe
import com.google.android.material.snackbar.Snackbar

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    private var titleText = ""
    private var imgRes = R.drawable.ic_launcher_foreground
    private var servings = 1

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // I used AI here since Interface didn't calculate recipes for required person
        val parcel: Recipe? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("recipe", Recipe::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("recipe")
        }
        if (parcel != null) {
            binding.recipe = parcel
            titleText = parcel.id
            servings = parcel.baseServings.coerceAtLeast(1)
        } else {
            titleText = intent.getStringExtra("recipeName").orEmpty()
            servings = intent.getIntExtra("servings", 1).coerceAtLeast(1)
        }

        imgRes = intent.getIntExtra("imageRes", R.drawable.ic_launcher_foreground)

        binding.imgRecipe.setImageResource(imgRes)
        if (titleText.isBlank()) titleText = getString(R.string.app_name)
        binding.textViewReceivedData.text = titleText
        binding.txtServings.text = "Base servings: $servings"
        binding.txtRecipe.text = recipeText(titleText, servings)
        Snackbar.make(binding.secondRoot, "Data received", Snackbar.LENGTH_SHORT).show()

        binding.seekServings.max = 9
        binding.seekServings.progress = servings - 1
        binding.seekServings.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, p: Int, fromUser: Boolean) {
                servings = (p + 1).coerceAtLeast(1)
                binding.txtServings.text = "Base servings: $servings"
                binding.txtRecipe.text = recipeText(titleText, servings)
            }
            override fun onStartTrackingTouch(sb: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {}
        })

        binding.buttonShowDialog.text = "Custom dialog"
        binding.buttonShowDialog.setOnClickListener { openCustomDialog() }

        binding.buttonShowAlert.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Info")
                .setMessage("Current servings: $servings\nRating will be on the next screen.")
                .setPositiveButton("OK", null)
                .show()
        }

        binding.buttonBack.setOnClickListener {
            val ret = Intent().apply {
                putExtra("recipeName", titleText)
                putExtra("imageRes", imgRes)
                putExtra("servings", servings)
            }
            setResult(Activity.RESULT_OK, ret)
            Toast.makeText(this, "Proceeding…", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun openCustomDialog() {
        val dialog = android.app.Dialog(this)
        dialog.setContentView(R.layout.dialog_custom)

        val input  = dialog.findViewById<android.widget.EditText>(R.id.editTextInput)
        val submit = dialog.findViewById<android.widget.Button>(R.id.buttonSubmit)

        input.setText(servings.toString())
        input.setSelection(input.text.length)

        submit.setOnClickListener {
            val v = input.text?.toString()?.toIntOrNull()
            val newS = (v ?: servings).coerceIn(1, 10)
            servings = newS
            binding.seekServings.progress = newS - 1
            binding.txtServings.text = "Base servings: $servings"
            binding.txtRecipe.text = recipeText(titleText, servings)
            Toast.makeText(this, "Servings: $servings", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }


    private fun recipeText(name: String, s: Int): String {
        val base = when (name.lowercase()) {
            "kettle pasta", "kettle tuna pasta" -> listOf(
                "Makarnayı ketılda haşla, süz; zeytinyağı ve tuzla çevir.",
                "Üstüne yoğurt+sarimsak ya da salça+sıcak su."
            )
            "mug omlette", "mug omlet", "mug omelette" -> listOf(
                "Kupaya yumurta, tuz, karabiber; çırp.",
                "Peynir ekle, mikrodalgada kısa aralıklarla pişir."
            )
            "toaster tost", "toaster quesadilla" -> listOf(
                "Tortilla/ekmeğe peynir ve malzeme koy.",
                "Tost makinesinde altın renk alınca çıkar."
            )
            "burnt cookie", "burned cookies", "çikolatalı kurabiye" -> listOf(
                "Tereyağı+şeker+un; küçük parçalar yap.",
                "Kısa süre pişir; kenarlar koyulaşmadan al."
            )
            else -> listOf(
                "Tuz-yağ-ısı: önce yüksek ısı, sonra kıs.",
                "Servisten önce az limon/sirke."
            )
        }
        val scale = when {
            s <= 2 -> "Az kişi: ölçüyü küçült, süre kısa."
            s <= 4 -> "Orta: temel ölçüler, tadına bak."
            s <= 6 -> "Kalabalık: tuzu kademeli artır."
            else   -> "Büyük porsiyon: parti parti pişir."
        }
        return (base + scale).joinToString("\n• ", prefix = "• ")
    }
}
