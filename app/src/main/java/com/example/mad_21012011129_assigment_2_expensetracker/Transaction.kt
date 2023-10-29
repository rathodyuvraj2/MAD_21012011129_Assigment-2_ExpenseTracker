package com.example.mad_21012011129_assigment_2_expensetracker

import java.io.Serializable

data class Transaction(
    val id: Int,
    val label: String,
    val amount: Double,
    val description: String
) : Serializable
