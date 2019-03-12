package com.zeus.s5next.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

class DbHelper(context: Context): SQLiteOpenHelper(context,"s5.db", null, 1) {

    private val TAG = DbHelper::class.java.simpleName

    abstract class Customer: BaseColumns{
        companion object {
            val TABLA_CUSTOMER = "Customer"
            val ID_CUSTOMER = "id_customer"
            val NAME = "name"
            val MIDDLE_NAME = "middle_name"
            val LAST_NAME = "last_name"
            val SECOND_LAST_NAME = "second_last_name"
            val BIRTHDATE = "birthdate"
            val GENDER = "gender"

            internal val crear_costumer = "CREATE TABLE $TABLA_CUSTOMER (" +
                    "$ID_CUSTOMER INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$NAME TEXT NOT NULL," +
                    "$MIDDLE_NAME TEXT," +
                    "$LAST_NAME TEXT NOT NULL," +
                    "$SECOND_LAST_NAME TEXT NOT NULL," +
                    "$BIRTHDATE DATE NOT NULL," +
                    "$GENDER INTEGER NOT NULL)"
            internal val drop_customer = "DROP TABLE IF EXISTS $TABLA_CUSTOMER"
        }
    }

    abstract class Payments: BaseColumns{
        companion object {
            val TABLA_PAYMENTS = "payments"
            val ID_PAYMENT = "id_payment"
            val ID_CUSTOMER = "id_customer"
            val REGISTER_DATE = "register_date"
            val DATE = "date"
            val AMOUNT = "amount"

            internal val crear_payments = "CREATE TABLE $TABLA_PAYMENTS(" +
                    "$ID_PAYMENT INTEGER PRIMARY KEY," +
                    "$ID_CUSTOMER INTEGER NOT NULL," +
                    "$REGISTER_DATE DATETIME NOT NULL," +
                    "$DATE DATE NOT NULL," +
                    "$AMOUNT REAL NOT NULL," +
                    "FOREIGN KEY ($ID_CUSTOMER) REFERENCES ${Customer.TABLA_CUSTOMER}(${Customer.ID_CUSTOMER}))"
            internal val drop_payments = "drop table if exists $TABLA_PAYMENTS"
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        Log.i(TAG,"creando base de datos" )
        db!!.execSQL(Customer.crear_costumer)
        db.execSQL(Payments.crear_payments)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.i(TAG,"eliminando base de datos")
        db!!.execSQL(Customer.drop_customer)
        db.execSQL(Payments.drop_payments)
        onCreate(db)
    }

}