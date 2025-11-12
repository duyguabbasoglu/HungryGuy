package com.example.duyguabbasoglu_hw1

import android.content.Context
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ctis487.lab.myapplication.LocaleHelper
import com.example.duyguabbasoglu_hw1.databinding.ActivityThirdBinding
import com.google.android.material.snackbar.Snackbar

class ThirdActivity : AppCompatActivity() {

    private lateinit var bindingThird: ActivityThirdBinding

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(
            LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingThird = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(bindingThird.root)

        val name = intent.getStringExtra("recipeName").orEmpty()
        val img  = intent.getIntExtra("imageRes", R.drawable.ic_launcher_foreground)
        val baseServings = intent.getIntExtra("servings", 1)

        bindingThird.imgFood.setImageResource(img)
        bindingThird.txtTitle.text = name

        // I got help from gpt when I calculate rating bar via seekbar
        bindingThird.txtRate.text = "Rate: 1 / 10"
        bindingThird.seekRate.max = 9
        bindingThird.seekRate.progress = 0
        bindingThird.seekRate.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
                val human = progress + 1
                bindingThird.txtRate.text = "Rate: $human / 10"
            }
            override fun onStartTrackingTouch(sb: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {}
        })

        bindingThird.btnRate.setOnClickListener {
            Snackbar.make(bindingThird.root, "Servings: $baseServings • Rating saved", Snackbar.LENGTH_LONG).show()
        }

        bindingThird.btnBackMain.setOnClickListener {
            displayToast("Going back to Main…")
            finish()
        }
    }

    fun displayToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
