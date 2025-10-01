package br.ufpr.walletapp.model

data class Transaction(
        val id: Long = 0,
        val type: String, // "DEBITO" ou "CREDITO"
        val description: String,
        val amount: Double
)
