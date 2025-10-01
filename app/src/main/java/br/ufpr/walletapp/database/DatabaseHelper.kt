package br.ufpr.walletapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.ufpr.walletapp.model.Transaction

class DatabaseHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "wallet.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_TRANSACTIONS = "transactions"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TYPE = "type"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_AMOUNT = "amount"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable =
                """
            CREATE TABLE $TABLE_TRANSACTIONS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TYPE TEXT NOT NULL,
                $COLUMN_DESCRIPTION TEXT NOT NULL,
                $COLUMN_AMOUNT REAL NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSACTIONS")
        onCreate(db)
    }

    fun insertTransaction(transaction: Transaction): Long {
        val db = writableDatabase
        val values =
                ContentValues().apply {
                    put(COLUMN_TYPE, transaction.type)
                    put(COLUMN_DESCRIPTION, transaction.description)
                    put(COLUMN_AMOUNT, transaction.amount)
                }
        return db.insert(TABLE_TRANSACTIONS, null, values)
    }

    fun getAllTransactions(): List<Transaction> {
        val transactions = mutableListOf<Transaction>()
        val db = readableDatabase
        val cursor =
                db.query(
                        TABLE_TRANSACTIONS,
                        arrayOf(COLUMN_ID, COLUMN_TYPE, COLUMN_DESCRIPTION, COLUMN_AMOUNT),
                        null,
                        null,
                        null,
                        null,
                        "$COLUMN_ID DESC"
                )

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(COLUMN_ID))
                val type = getString(getColumnIndexOrThrow(COLUMN_TYPE))
                val description = getString(getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val amount = getDouble(getColumnIndexOrThrow(COLUMN_AMOUNT))
                transactions.add(Transaction(id, type, description, amount))
            }
            close()
        }
        return transactions
    }

    fun getTransactionsByType(type: String): List<Transaction> {
        val transactions = mutableListOf<Transaction>()
        val db = readableDatabase
        val cursor =
                db.query(
                        TABLE_TRANSACTIONS,
                        arrayOf(COLUMN_ID, COLUMN_TYPE, COLUMN_DESCRIPTION, COLUMN_AMOUNT),
                        "$COLUMN_TYPE = ?",
                        arrayOf(type),
                        null,
                        null,
                        "$COLUMN_ID DESC"
                )

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(COLUMN_ID))
                val typeVal = getString(getColumnIndexOrThrow(COLUMN_TYPE))
                val description = getString(getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val amount = getDouble(getColumnIndexOrThrow(COLUMN_AMOUNT))
                transactions.add(Transaction(id, typeVal, description, amount))
            }
            close()
        }
        return transactions
    }

    fun getBalance(): Double {
        var balance = 0.0
        val db = readableDatabase
        val cursor =
                db.query(
                        TABLE_TRANSACTIONS,
                        arrayOf(COLUMN_TYPE, COLUMN_AMOUNT),
                        null,
                        null,
                        null,
                        null,
                        null
                )

        with(cursor) {
            while (moveToNext()) {
                val type = getString(getColumnIndexOrThrow(COLUMN_TYPE))
                val amount = getDouble(getColumnIndexOrThrow(COLUMN_AMOUNT))
                if (type == "CREDITO") {
                    balance += amount
                } else {
                    balance -= amount
                }
            }
            close()
        }
        return balance
    }
}
