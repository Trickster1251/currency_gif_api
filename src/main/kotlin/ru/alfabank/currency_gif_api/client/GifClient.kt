package ru.alfabank.currency_gif_api.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import ru.alfabank.currency_gif_api.model.Gif
import java.net.URI

@FeignClient(name = "Gif", url = "https://api.giphy.com/")
interface GifClient {
    @GetMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun getGif(baseUrl: URI): Gif?
}