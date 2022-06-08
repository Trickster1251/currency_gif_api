package ru.alfabank.currency_gif_api.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import ru.alfabank.currency_gif_api.model.Exchange
import java.net.URI

@FeignClient(name = "ExchCourse", url = "https://openexchangerates.org/api/")
interface CurrencyClient {

    @GetMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun getExchange(baseUrl: URI?): Exchange
}