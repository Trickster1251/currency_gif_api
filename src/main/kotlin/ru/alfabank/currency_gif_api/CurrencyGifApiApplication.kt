package ru.alfabank.currency_gif_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class CurrencyGifApiApplication

fun main(args: Array<String>) {
    runApplication<CurrencyGifApiApplication>(*args)
}
