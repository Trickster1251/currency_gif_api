package ru.alfabank.currency_gif_api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.alfabank.currency_gif_api.error.Error

@RestController
@RequestMapping("/error")
class ErrorController : org.springframework.boot.web.servlet.error.ErrorController{

    @GetMapping()
    fun error() : Error {
        return Error(
            405,
            "currency is not valid"
        )
    }
}