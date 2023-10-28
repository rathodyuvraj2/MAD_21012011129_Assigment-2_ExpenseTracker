package com.example.mad_21012011129_assigment_2_expensetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class MainActivity : AppCompatActivity() {

    private lateinit var deletedTransaction: Transaction
    private lateinit var transactions : List<Transaction>
    private lateinit var oldTransactions : List<Transaction>
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        transactions = arrayListOf(
            Transaction("wekend budget",400.0),
            Transaction("wekend budget",-400.0),
            Transaction("wekend budget",400.0),
            Transaction("wekend budget",-400.0),
            Transaction("wekend budget",400.0),
            Transaction("wekend budget",400.0),
            Transaction("wekend budget",-400.0),
            Transaction("wekend budget",400.0),
            Transaction("wekend budget",400.0),
            Transaction("wekend budget",400.0),
            Transaction("wekend budget",400.0),
            Transaction("wekend budget",400.0),
        )
        transactionAdapter = TransactionAdapter(transactions)
        linearLayoutManager =  LinearLayoutManager(this)



        val recycleview = findViewById<RecyclerView>(R.id.recyclerview)

        recycleview.apply {
            adapter = transactionAdapter
            layoutManager = linearLayoutManager
        }
        updateDashboard()

    }
    private fun updateDashboard(){
        val totalAmount : Double = transactions.map { it.amount }.sum()
        val budgetAmount : Double = transactions.filter { it.amount>0 }.map { it.amount }.sum()
        val expenseAmount : Double = totalAmount - budgetAmount

      val budget = findViewById<TextView>(R.id.budget)
        val expense = findViewById<TextView>(R.id.expense)
        val balance = findViewById<TextView>(R.id.balance)
        balance.text = "$ %2f".format(totalAmount)
        expense.text = "$ %2f".format(expenseAmount)
        budget.text = "$ %2f".format(budgetAmount)

    }
}