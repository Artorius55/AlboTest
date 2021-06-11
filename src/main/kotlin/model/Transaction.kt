package model

import java.math.BigInteger
import java.util.*

data class Transaction(
    val uuid: BigInteger,
    val description: String,
    val category: String,
    val operation: String,
    val amount: Double,
    val status: String,
    val creation_date: Date
    )