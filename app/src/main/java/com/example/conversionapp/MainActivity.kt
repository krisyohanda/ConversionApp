package com.example.conversionapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.conversionapp.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.conversionButton.setOnClickListener { calculateConversion() }

        binding.nilaiMataUangEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
    }

    fun calculateConversion() {
        val stringInTextField = binding.nilaiMataUangEditText.text.toString()
        val nilaiMataUang = stringInTextField.toDoubleOrNull()
        if (nilaiMataUang == null){
            binding.conversionResult.text = ""
            return
        }

        val valueForeignCurrency = when(binding.conversionOptions.checkedRadioButtonId){
            R.id.option_euro -> 15548.12
            R.id.option_dollar -> 14000.00
            R.id.option_yen -> 114.22
            else -> 3830.34
        }

        val conversion = valueForeignCurrency * nilaiMataUang

        val indonesianLocale = Locale("in", "ID")
        val formattedConversion = NumberFormat.getCurrencyInstance(indonesianLocale).format(conversion)
        binding.conversionResult.text = getString(R.string.conversion_amount, formattedConversion)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}