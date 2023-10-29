package com.example.mad_21012011129_assigment_2_expensetracker

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class TransactionDao(context: Context) {
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    fun insertTransaction(transaction: Transaction) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_LABEL, transaction.label)
            put(DatabaseHelper.COLUMN_AMOUNT, transaction.amount)
            put(DatabaseHelper.COLUMN_DESCRIPTION, transaction.description)
        }
        db.insert(DatabaseHelper.TABLE_TRANSACTIONS, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getAllTransactions(): List<Transaction> {
        val transactions = mutableListOf<Transaction>()
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM ${DatabaseHelper.TABLE_TRANSACTIONS}"

        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("_id"))
                val label = cursor.getString(cursor.getColumnIndex("label"))
                val amount = cursor.getDouble(cursor.getColumnIndex("amount"))
                val description = cursor.getString(cursor.getColumnIndex("description"))

                val transaction = Transaction(id, label, amount, description)
                transactions.add(transaction)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return transactions
    }
}
