package br.ufpr.walletapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import br.ufpr.walletapp.R
import br.ufpr.walletapp.model.Transaction
import java.text.NumberFormat
import java.util.Locale

class TransactionAdapter(
        private val context: Context,
        private val transactions: List<Transaction>
) : BaseAdapter() {

    override fun getCount(): Int = transactions.size

    override fun getItem(position: Int): Any = transactions[position]

    override fun getItemId(position: Int): Long = transactions[position].id

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View =
                convertView
                        ?: LayoutInflater.from(context)
                                .inflate(R.layout.item_transaction, parent, false)

        val transaction = transactions[position]

        val imgType = view.findViewById<ImageView>(R.id.imgType)
        val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
        val tvAmount = view.findViewById<TextView>(R.id.tvAmount)

        if (transaction.type == "CREDITO") {
            imgType.setImageResource(R.drawable.ic_credit)
        } else {
            imgType.setImageResource(R.drawable.ic_debit)
        }

        tvDescription.text = transaction.description

        val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        tvAmount.text = format.format(transaction.amount)

        return view
    }
}
