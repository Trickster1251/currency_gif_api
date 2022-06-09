package ru.alfabank.currency_gif_api.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.alfabank.currency_gif_api.model.Gif
import ru.alfabank.currency_gif_api.service.GifService

@RestController
@RequestMapping("/api" ,produces = [MediaType.APPLICATION_JSON_VALUE])
class CurrencyController(
    @Autowired private val service: GifService,
    @Value("\${currency.default}") val defaultExchangeCurrency : String

) {

    @GetMapping("/")
    fun getDefaultCurrency() : Gif? {
        return service.getGif(defaultExchangeCurrency)
    }

    @GetMapping("/{currency}")
    fun getJsonData(@PathVariable currency: String) : Gif? {
        return   service.getGif(currency)
    }
}