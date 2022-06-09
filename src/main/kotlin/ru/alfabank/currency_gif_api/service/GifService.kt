package ru.alfabank.currency_gif_api.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import ru.alfabank.currency_gif_api.client.CurrencyClient
import ru.alfabank.currency_gif_api.client.GifClient
import ru.alfabank.currency_gif_api.enum.CurrencyEnum
import ru.alfabank.currency_gif_api.model.Gif
import ru.alfabank.currency_gif_api.service.ExchangeRateComporator.compareExchangeRate
import java.net.URI
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class GifService(
    @Autowired val client: CurrencyClient,
    @Autowired val gifClient: GifClient,

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

    companion object {
        fun getExchangeURI(exServer: String, exchangeAppID: String, curBase: String, date: LocalDateTime): URI {
            val timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
            val uri = "${exServer}historical/${timeFormatter.format(date)}.json?app_id=${exchangeAppID}&base=${curBase}"

            return URI.create(uri)
        }

        fun getGifURI(gifServer: String, gifApiID: String, tag: String): URI {
            val uri = "${gifServer}v1/gifs/random?api_key=${gifApiID}&tag=${tag}"

            return URI.create(uri)
        }
    }
    fun getGif(currency : String): Gif {

        val currencyToday =
            client.getExchange(getExchangeURI(exServer, exchangeAppID, curBase, LocalDateTime.now()))

        val currencyYesterday =
            client.getExchange(
                    getExchangeURI(
                    exServer,
                    exchangeAppID,
                    curBase,
                    LocalDateTime.now().minusDays(1)
                )
            )

        val tag = getGifTag(compareExchangeRate(currencyToday, currencyYesterday, currency))


        return gifClient.getGif(getGifURI(gifServer, gifApiID, tag)) ?: throw Exception("Null")
    }

    fun getGifTag(b: Boolean): String {
        return if (b) rich else poor
    }

    fun isValidCurrency(currency: String) : Boolean{
        val allCurrency = CurrencyEnum.values()
        for(d in allCurrency) {
            if((d.name) == currency.uppercase()){
                return true
            }
        }
        throw java.lang.Exception()
    }
}