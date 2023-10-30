package com.example.mad_21012011129_assigment_2_expensetracker

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var deleteTransaction: Transaction
    private lateinit var transactions: List<Transaction>
    private lateinit var oldtransactions: List<Transaction>
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addBtn = findViewById<FloatingActionButton>(R.id.addBtn)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        transactions = ArrayList()
        transactionAdapter = TransactionAdapter(transactions)
        linearLayoutManager = LinearLayoutManager(this)
        dbHelper = DatabaseHelper(this)

        recyclerView.apply {
            adapter = transactionAdapter
            layoutManager = linearLayoutManager
        }

        //swipe to remove

     val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
               return  false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               deleteTransaction(transactions[viewHolder.adapterPosition])
            }

        }


        addBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddTransactionActivity::class.java))
        }
    }

    private fun deleteTransaction(transaction: Transaction) {
        val transactionId = transaction.id

        GlobalScope.launch {
            // Delete the transaction from the SQLite database
            dbHelper.writableDatabase.use { db ->
                db.delete(DatabaseHelper.TABLE_TRANSACTIONS, "${DatabaseHelper.COLUMN_ID} = ?", arrayOf(transactionId.toString()))
            }

            // Update the transactions list by filtering out the deleted transaction
            transactions = transactions.filter { it.id != transactionId }

            runOnUiThread {
                updateDashboard()
                transactionAdapter.setData(transactions)
                showSnackbar()
            }
        }
    }

    private fun showSnackbar() {
        val snackbarMessage = "Item deleted"

        Snackbar.make(findViewById(R.id.coordinatorLayout), snackbarMessage, Snackbar.LENGTH_SHORT)
            .setAction("Undo") {
                // Implement undo functionality if needed
            }
            .show()
    }

    override fun onResume() {
        super.onResume()
        fetchAll()
    }

    private fun fetchAll() {
        transactions = dbHelper.getAllTransactions()
        updateDashboard()
        transactionAdapter.setData(transactions)
    }

    private fun updateDashboard() {
        val totalAmount = transactions.sumByDouble { it.amount }
        val budgetAmount = transactions.filter { it.amount > 0 }.sumByDouble { it.amount }
        val expenseAmount = totalAmount - budgetAmount

        val budget = findViewById<TextView>(R.id.budget)
        val expense = findViewById<TextView>(R.id.expense)
        val balance = findViewById<TextView>(R.id.balance)

        balance.text = String.format("$%.2f", totalAmount)
        expense.text = String.format("$%.2f", expenseAmount)
        budget.text = String.format("$%.2f", budgetAmount)
    }
}
