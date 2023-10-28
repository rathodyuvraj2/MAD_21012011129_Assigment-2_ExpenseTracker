package com.example.mad_21012011129_assigment_2_expensetracker

import android.annotation.SuppressLint
import android.icu.text.AlphabeticIndex.Bucket.LabelType
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text

class AddTransactionActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        val addTransactionBtn = findViewById<Button>(R.id.addTransactionBtn)
        val labelInput = findViewById<TextView>(R.id.labelInput)
        val amountInput = findViewById<TextView>(R.id.amountInput)
        val labelLayout = findViewById<TextView>(R.id.labelLayout)
        val amountLayout = findViewById<TextView>(R.id.amountLayout)
        val closeBtn= findViewById<Button>(R.id.closeBtn)

        labelInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s?.isNotEmpty() == true) {
                    labelLayout.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed for your case
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed for your case
            }
        })

        amountInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s?.isNotEmpty() == true) {
                    amountLayout.error = null
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed for your case
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed for your case
            }

        })

        addTransactionBtn.setOnClickListener {
            val label: String = labelInput.text.toString()
            val amount: Double? = amountInput.text.toString().toDoubleOrNull()
            if (label.isEmpty()) {
                labelLayout.error = "Please enter a valid label"
            }

            if (amount == null) {
                amountLayout.error = "Please enter the valid amount"
            }
        }

        closeBtn.setOnClickListener {
            finish()
        }
    }
}







