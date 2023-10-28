package com.example.mad_21012011129_assigment_2_expensetracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val label:String ,
    val amount: Double,
    val description: String) {
}