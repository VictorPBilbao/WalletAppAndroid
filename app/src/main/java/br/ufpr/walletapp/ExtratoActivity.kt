package br.ufpr.walletapp

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.ufpr.walletapp.adapter.TransactionAdapter
import br.ufpr.walletapp.database.DatabaseHelper
import br.ufpr.walletapp.model.Transaction
import java.text.NumberFormat
import java.util.Locale

class ExtratoActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var listView: ListView
    private lateinit var tvBalance: TextView
    private lateinit var btnAll: Button
    private lateinit var btnDebito: Button
    private lateinit var btnCredito: Button
    private lateinit var btnBack: Button
    private lateinit var adapter: TransactionAdapter
    private var currentFilter = "ALL"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extrato)

        dbHelper = DatabaseHelper(this)

        listView = findViewById(R.id.listViewTransactions)
        tvBalance = findViewById(R.id.tvBalance)
        btnAll = findViewById(R.id.btnAll)
        btnDebito = findViewById(R.id.btnDebito)
        btnCredito = findViewById(R.id.btnCredito)
        btnBack = findViewById(R.id.btnBack)

        loadTransactions("ALL")

        btnAll.setOnClickListener {
            currentFilter = "ALL"
            loadTransactions("ALL")
        }

        btnDebito.setOnClickListener {
            currentFilter = "DEBITO"
            loadTransactions("DEBITO")
        }

        btnCredito.setOnClickListener {
            currentFilter = "CREDITO"
            loadTransactions("CREDITO")
        }

        btnBack.setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        loadTransactions(currentFilter)
    }

    private fun loadTransactions(filter: String) {
        val transactions: List<Transaction> =
                when (filter) {
                    "ALL" -> dbHelper.getAllTransactions()
                    "DEBITO" -> dbHelper.getTransactionsByType("DEBITO")
                    "CREDITO" -> dbHelper.getTransactionsByType("CREDITO")
                    else -> dbHelper.getAllTransactions()
                }

        adapter = TransactionAdapter(this, transactions)
        listView.adapter = adapter

        val balance = dbHelper.getBalance()
        val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        tvBalance.text = "Saldo: ${format.format(balance)}"
    }
}
