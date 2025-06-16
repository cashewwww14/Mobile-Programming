// MainActivity.kt
package com.example.moneychanger

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var etAmount: EditText
    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var tvResult: TextView
    private lateinit var btnSwap: Button
    private lateinit var tvRate: TextView

    // Data mata uang dengan rate terhadap USD
    private val currencies = mapOf(
        "USD" to 1.0,
        "IDR" to 15750.0,
        "EUR" to 0.85,
        "GBP" to 0.73,
        "JPY" to 110.0,
        "SGD" to 1.35,
        "MYR" to 4.12,
        "THB" to 33.5,
        "AUD" to 1.45,
        "CAD" to 1.25,
        "CNY" to 6.45,
        "KRW" to 1180.0
    )

    private val currencyNames = currencies.keys.toList()
    private val decimalFormat = DecimalFormat("#,##0.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupSpinners()
        setupListeners()

        // Set default values
        spinnerFrom.setSelection(currencyNames.indexOf("USD"))
        spinnerTo.setSelection(currencyNames.indexOf("IDR"))
        updateConversion()
    }

    private fun initViews() {
        etAmount = findViewById(R.id.etAmount)
        spinnerFrom = findViewById(R.id.spinnerFrom)
        spinnerTo = findViewById(R.id.spinnerTo)
        tvResult = findViewById(R.id.tvResult)
        btnSwap = findViewById(R.id.btnSwap)
        tvRate = findViewById(R.id.tvRate)
    }

    private fun setupSpinners() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter
    }

    private fun setupListeners() {
        etAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateConversion()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateConversion()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateConversion()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        btnSwap.setOnClickListener {
            swapCurrencies()
        }
    }

    private fun updateConversion() {
        val amountText = etAmount.text.toString()
        if (amountText.isEmpty()) {
            tvResult.text = "0.00"
            tvRate.text = ""
            return
        }

        try {
            val amount = amountText.toDouble()
            val fromCurrency = currencyNames[spinnerFrom.selectedItemPosition]
            val toCurrency = currencyNames[spinnerTo.selectedItemPosition]

            val result = convertCurrency(amount, fromCurrency, toCurrency)
            tvResult.text = decimalFormat.format(result)

            // Show exchange rate
            val rate = getExchangeRate(fromCurrency, toCurrency)
            tvRate.text = "1 $fromCurrency = ${decimalFormat.format(rate)} $toCurrency"

        } catch (e: NumberFormatException) {
            tvResult.text = "Invalid amount"
            tvRate.text = ""
        }
    }

    private fun convertCurrency(amount: Double, from: String, to: String): Double {
        val fromRate = currencies[from] ?: 1.0
        val toRate = currencies[to] ?: 1.0

        // Convert to USD first, then to target currency
        val usdAmount = amount / fromRate
        return usdAmount * toRate
    }

    private fun getExchangeRate(from: String, to: String): Double {
        val fromRate = currencies[from] ?: 1.0
        val toRate = currencies[to] ?: 1.0
        return toRate / fromRate
    }

    private fun swapCurrencies() {
        val fromPosition = spinnerFrom.selectedItemPosition
        val toPosition = spinnerTo.selectedItemPosition

        spinnerFrom.setSelection(toPosition)
        spinnerTo.setSelection(fromPosition)
    }
}
