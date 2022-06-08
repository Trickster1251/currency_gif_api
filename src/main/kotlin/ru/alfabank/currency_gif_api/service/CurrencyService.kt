package ru.alfabank.currency_gif_api.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import ru.alfabank.currency_gif_api.client.CurrencyClient
import ru.alfabank.currency_gif_api.client.GifClient
import ru.alfabank.currency_gif_api.model.Exchange
import ru.alfabank.currency_gif_api.model.Gif
import java.net.URI
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class CurrencyService(
    @Autowired private val client: CurrencyClient,
    @Autowired private val gifClient: GifClient,

    @Value("\${currency.base}")
    val curBase: String,
    @Value("\${currency.server}")
    val exServer: String,
    @Value("\${currency.app_id}")
    val exchangeAppID: String,
    @Value("\${gif.server}")
    val gifServer: String,
    @Value("\${gif.app_id}")
    val gifApiID: String,
    @Value("\${gif.rich}")
    val rich: String,
    @Value("\${gif.poor}")
    val poor: String,
) {

    fun getGifAtJson(currency : String): Gif {
        val exchangeToday =
            client.getExchange(getExchangeURI(exServer, exchangeAppID, curBase, LocalDateTime.now()))

        val exchangeYesterday =
            client.getExchange(
                    getExchangeURI(
                    exServer,
                    exchangeAppID,
                    curBase,
                    LocalDateTime.now().minusDays(1)
                )
            )

        val tag = getGifTag(compareExchangeRate(exchangeToday, exchangeYesterday, currency))

        return gifClient.getGif(getGifURI(gifServer, gifApiID, tag))
    }

    fun getExchangeURI(exServer: String, exchangeAppID: String, curBase: String, date: LocalDateTime): URI {
        val builderURL = StringBuilder()
        val timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)

        if (date.toLocalDate().isEqual(LocalDate.now())) {
            builderURL
                .append(exServer)
                .append("latest.json?app_id=")
                .append(exchangeAppID)
                .append("&base=")
                .append(curBase.uppercase(Locale.getDefault()))
            return URI.create(builderURL.toString())
        }
        builderURL
            .append(exServer)
            .append("historical/")
            .append(timeFormatter.format(date))
            .append(".json?app_id=")
            .append(exchangeAppID)
            .append("&base=")
            .append(curBase.uppercase(Locale.getDefault()))
        return URI.create(builderURL.toString())
    }

    fun getGifURI(gifServer: String, gifApiID: String, tag: String): URI {
        val builderURL = StringBuilder()
        builderURL
            .append(gifServer)
            .append("v1/gifs/random?api_key=")
            .append(gifApiID)
            .append("&tag=")
            .append(tag)
        return URI.create(builderURL.toString())
    }
    fun compareExchangeRate(exch1: Exchange, exch2: Exchange, currency: String): Boolean {
        return exch1.rates!![currency.uppercase(Locale.getDefault())]!! > exch2.rates!![currency.uppercase(Locale.getDefault())]!!
    }

    fun getGifTag(b: Boolean): String {
        return if (b) rich else poor
    }
}