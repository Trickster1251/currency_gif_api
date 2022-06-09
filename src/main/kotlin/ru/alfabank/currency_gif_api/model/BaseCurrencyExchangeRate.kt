package ru.alfabank.currency_gif_api.model

class BaseCurrencyExchangeRate (
    //    base: "USD",
    //    rates: {
    //        AED: 3.672538,
    //        AFN: 66.809999,
    //        /* ... */
    //    }
    var rates: Map<String, Double>?,
)