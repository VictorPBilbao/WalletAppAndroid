package br.ufpr.walletapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.ufpr.walletapp.database.DatabaseHelper
import br.ufpr.walletapp.model.Transaction

class CadastroActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioDebito: RadioButton
    private lateinit var radioCredito: RadioButton
    private lateinit var editDescription: EditText
    private lateinit var editAmount: EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        dbHelper = DatabaseHelper(this)

        radioGroup = findViewById(R.id.radioGroup)
        radioDebito = findViewById(R.id.radioDebito)
        radioCredito = findViewById(R.id.radioCredito)
        editDescription = findViewById(R.id.editDescription)
        editAmount = findViewById(R.id.editAmount)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)

        btnSave.setOnClickListener { saveTransaction() }

        btnCancel.setOnClickListener { finish() }
    }

    private fun saveTransaction() {
        val description = editDescription.text.toString().trim()
        val amountStr = editAmount.text.toString().trim()

        if (description.isEmpty()) {
            Toast.makeText(this, "Por favor, insira uma descrição", Toast.LENGTH_SHORT).show()
            return
        }

        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Por favor, insira um valor", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountStr.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            Toast.makeText(this, "Por favor, insira um valor válido", Toast.LENGTH_SHORT).show()
            return
        }

        val type = if (radioDebito.isChecked) "DEBITO" else "CREDITO"

        val transaction = Transaction(type = type, description = description, amount = amount)

        val id = dbHelper.insertTransaction(transaction)
        if (id > 0) {
            Toast.makeText(this, "Transação cadastrada com sucesso!", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Erro ao cadastrar transação", Toast.LENGTH_SHORT).show()
        }
    }
}
