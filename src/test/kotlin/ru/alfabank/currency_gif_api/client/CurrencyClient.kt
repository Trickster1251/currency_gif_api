package ru.alfabank.currency_gif_api.client

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import ru.alfabank.currency_gif_api.model.BaseCurrencyExchangeRate
import java.time.LocalDateTime
import ru.alfabank.currency_gif_api.service.GifService
import ru.alfabank.currency_gif_api.service.GifService.Companion.getExchangeURI

@SpringBootTest
internal class ExchangeServiceTest(
    @Value("\${currency.server}")
    private val exServer: String,
    @Value("\${currency.app_id}")
    private val currencyAppID: String,
    @Value("\${currency.base}")
    private val curBase: String,
    @Autowired
    private var currencyClient: CurrencyClient

) {
    @Test
    fun exchange(): Unit {
            val exchange: BaseCurrencyExchangeRate =
                currencyClient.getExchange(baseUrl = getExchangeURI(exServer, currencyAppID, curBase, LocalDateTime.now()))
            Assertions.assertNotNull(exchange)
    }
}