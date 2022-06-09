package ru.alfabank.currency_gif_api.service

import ru.alfabank.currency_gif_api.model.BaseCurrencyExchangeRate
import java.util.*

object ExchangeRateComporator {
    fun compareExchangeRate(exch1: BaseCurrencyExchangeRate, exch2: BaseCurrencyExchangeRate, currency: String): Boolean {
        return exch1.rates!![currency.uppercase(Locale.getDefault())]!! > exch2.rates!![currency.uppercase(Locale.getDefault())]!!
    }
}