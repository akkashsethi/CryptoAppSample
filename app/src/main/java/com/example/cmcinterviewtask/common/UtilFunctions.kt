package com.example.cmcinterviewtask.common

import java.text.NumberFormat
import java.util.Currency

fun Double.toCurrency(): String {
    val format = NumberFormat.getCurrencyInstance()
    format.currency = Currency.getInstance(Constants.CURRENCY)
    format.maximumFractionDigits = 2
    return format.format(this)
}


fun String.toCurrency(

): String {
    val value = this.toDoubleOrNull() ?: 0.0

    val format = NumberFormat.getCurrencyInstance()
    format.currency = Currency.getInstance(Constants.CURRENCY)
    format.maximumFractionDigits = 0 // market cap usually has no decimals

    return format.format(value)
}


fun Long.toCurrency(

    maxFractionDigits: Int = 0
): String {
    val format = NumberFormat.getCurrencyInstance()
    format.currency = Currency.getInstance(Constants.CURRENCY)
    format.maximumFractionDigits = maxFractionDigits
    return format.format(this)
}