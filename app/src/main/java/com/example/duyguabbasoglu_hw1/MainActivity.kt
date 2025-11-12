package com.example.duyguabbasoglu_hw1

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ctis487.lab.myapplication.LocaleHelper
import com.example.duyguabbasoglu_hw1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding : ActivityMainBinding

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

                when (position) {
                    0 -> mainBinding.imgFood.setImageResource(R.drawable.burnt_cookie)
                    1 -> mainBinding.imgFood.setImageResource(R.drawable.kettle_pasta)
                    2 -> mainBinding.imgFood.setImageResource(R.drawable.mug_omlette)
                    3 -> mainBinding.imgFood.setImageResource(R.drawable.toaster_tost)
                    else -> mainBinding.imgFood.setImageResource(R.drawable.ic_launcher_foreground)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        mainBinding.btnGo.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("recipeName", mainBinding.recipes.selectedItem?.toString().orEmpty())
            startActivity(intent)
        }

        mainBinding.fabLang.setOnClickListener {
            changeLanguage()
        }
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

    fun changeLanguage() {
        val currentLang = LocaleHelper.getLanguage(this)
        val newLang = if (currentLang == "tr") "en" else "tr"
        LocaleHelper.setLocale(this, newLang)
        displayToast(resources.getString(R.string.msgtoast))
        recreate()
    }

}