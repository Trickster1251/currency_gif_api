package ru.alfabank.currency_gif_api.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.alfabank.currency_gif_api.model.Gif
import ru.alfabank.currency_gif_api.service.CurrencyService

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class CurrencyController(
    @Autowired currencyService: CurrencyService
) {

    private val service = currencyService

    @GetMapping("/{currency}")
    fun getJsonData(@PathVariable currency: String) : Gif {
        return service.getGifAtJson(currency)
    }

}