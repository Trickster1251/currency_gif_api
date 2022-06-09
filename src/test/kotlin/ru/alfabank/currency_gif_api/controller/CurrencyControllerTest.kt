package ru.alfabank.currency_gif_api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.alfabank.currency_gif_api.client.CurrencyClient
import ru.alfabank.currency_gif_api.client.GifClient
import ru.alfabank.currency_gif_api.model.BaseCurrencyExchangeRate
import ru.alfabank.currency_gif_api.model.Data
import ru.alfabank.currency_gif_api.model.Gif
import ru.alfabank.currency_gif_api.service.GifService.Companion.getExchangeURI
import ru.alfabank.currency_gif_api.service.GifService.Companion.getGifURI
import java.net.URI
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = ["classpath:application.properties"])
internal class CurrencyControllerTest(
    @Value("\${currency.server}")
    private val exServer: String,
    @Value("\${currency.app_id}")
    private val currencyAppID: String,
    @Value("\${currency.base}")
    private val curBase: String,
    @Value("\${gif.server}")
    private val gifServer: String,
    @Value("\${gif.app_id}")
    private val gifApiID: String,
    @Value("\${gif.rich}")
    private val rich: String,
    @Value("\${gif.poor}")
    private val broken: String,


    @Autowired
    private val objectMapper: ObjectMapper,

    @Autowired
    private val mockMvc: MockMvc,

    @Autowired
    @MockBean
    private var currencyClient: CurrencyClient,

    @Autowired
    @MockBean
    private var gifClient: GifClient,


    ) {


    final var exMap: MutableMap<String, Double> = HashMap()

    init {
        exMap["GBP"] = 1.77
        exMap["RUB"] = 77.6
    }

    var Currency: BaseCurrencyExchangeRate = BaseCurrencyExchangeRate( exMap)
    var Currency1: BaseCurrencyExchangeRate = BaseCurrencyExchangeRate( exMap)
    var testData: Data = Data(
        "gg85uBnucTSnyBDsAb",
        "love and hip hop money GIF by VH1",
        "g",
        "https://giphy.com/embed/gg85uBnucTSnyBDsAb",
        "https://giphy.com/gifs/vh1-gg85uBnucTSnyBDsAb"
    )
    var testGif: Gif = Gif(testData)


    @Throws(Exception::class)
    protected fun perform(builder: MockHttpServletRequestBuilder?): ResultActions {
        return mockMvc!!.perform(builder!!)
    }

    @BeforeEach
    fun beforeEach() {
        val exToday: URI = getExchangeURI(exServer, currencyAppID, curBase, LocalDateTime.now())
        val exYest: URI = getExchangeURI(exServer, currencyAppID, curBase, LocalDateTime.now().minusDays(1))
        val gifURI: URI = getGifURI(gifServer, gifApiID, broken)
        Mockito.`when`(currencyClient.getExchange(exToday)).thenReturn(Currency)
        Mockito.`when`(currencyClient.getExchange(exYest)).thenReturn(Currency1)
        Mockito.`when`(gifClient.getGif(gifURI)).thenReturn(testGif)
    }

    @Throws(Exception::class)
    @Test
    fun gif(): Unit{
        perform(MockMvcRequestBuilders.get("/api/"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(testGif)))
    }

    @Throws(Exception::class)
    @Test
    fun gifAsJSON(): Unit
    {
        perform(MockMvcRequestBuilders.get("/api/gbp"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(testGif)))
    }
}