package com.zeus.s5next.Data

data class Pagos(var id_customer: Int) {
    var id_payment = 0
    lateinit var fechaReg: String
    lateinit var fechaPago: String
    var monto: Double = 0.0

}