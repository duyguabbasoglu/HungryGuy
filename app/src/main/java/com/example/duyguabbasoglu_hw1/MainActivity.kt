package com.example.duyguabbasoglu_hw1

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.ctis487.lab.myapplication.LocaleHelper
import com.example.duyguabbasoglu_hw1.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.example.duyguabbasoglu_hw1.model.Recipe
import com.example.duyguabbasoglu_hw1.model.BurnedCookies
import com.example.duyguabbasoglu_hw1.model.KettlePasta
import com.example.duyguabbasoglu_hw1.model.MugOmlette
import com.example.duyguabbasoglu_hw1.model.RecipeBase
import com.example.duyguabbasoglu_hw1.model.ToasterQuesadilla

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private var lastSelectedPos: Int = 0

    private val toSecond = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { res ->
        if (res.resultCode == RESULT_OK) {
            val data = res.data
            val name = data?.getStringExtra("recipeName").orEmpty()
            val img  = data?.getIntExtra("imageRes", R.drawable.ic_launcher_foreground)
                ?: R.drawable.ic_launcher_foreground
            val srv  = data?.getIntExtra("servings", 1) ?: 1

            startActivity(
                Intent(this, ThirdActivity::class.java).apply {
                    putExtra("recipeName", name)
                    putExtra("imageRes", img)
                    putExtra("servings", srv)
                }
            )
        } else {
            displaySnack("No data returned")
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        startBlink(mainBinding.txtApp)
        colorAnimation()

        mainBinding.recipes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                lastSelectedPos = position
                mainBinding.imgFood.setImageResource(imgPos(position))
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        mainBinding.btnGo.setOnClickListener {
            val pos = mainBinding.recipes.selectedItemPosition
            val name = mainBinding.recipes.selectedItem?.toString().orEmpty()
            val imgRes = imgPos(pos)

            val base: RecipeBase = when (pos) {
                0 -> BurnedCookies()
                1 -> KettlePasta()
                2 -> MugOmlette()
                3 -> ToasterQuesadilla()
                else -> BurnedCookies()
            }

            val recipe = Recipe(
                id = name,
                base = base,
                baseServings = 1
            )

            toSecond.launch(
                Intent(this, SecondActivity::class.java).apply {
                    putExtra("recipe", recipe)
                    putExtra("imageRes", imgRes)
                }
            )
        }

        mainBinding.fabLang.setOnClickListener { changeLanguage() }
    }

    fun startBlink(view: View) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink))
    }

    private fun colorAnimation() {
        val color = object : Runnable {
            override fun run() {
                val r = (Math.random() * 255).toInt()
                val g = (Math.random() * 255).toInt()
                val b = (Math.random() * 255).toInt()
                mainBinding.txtApp.setTextColor(android.graphics.Color.rgb(r, g, b))
                mainBinding.txtApp.postDelayed(this, 400)
            }
        }
        mainBinding.txtApp.removeCallbacks(color)
        mainBinding.txtApp.post(color)
    }

    fun displayToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun displaySnack(message: String, anchor: View = mainBinding.root) {
        Snackbar.make(anchor, message, Snackbar.LENGTH_LONG).show()
    }

    fun changeLanguage() {
        val currentLang = LocaleHelper.getLanguage(this)
        val newLang = if (currentLang == "tr") "en" else "tr"
        LocaleHelper.setLocale(this, newLang)
        displayToast(resources.getString(R.string.msgtoast))
        recreate()
    }

    private fun imgPos(position: Int): Int = when (position) {
        0 -> R.drawable.burnt_cookie
        1 -> R.drawable.kettle_pasta
        2 -> R.drawable.mug_omlette
        3 -> R.drawable.toaster_tost
        else -> R.drawable.ic_launcher_foreground
    }
}
