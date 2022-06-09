package ru.alfabank.currency_gif_api.property

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PropertiesTest(
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
) {

    @Test
    @DisplayName("All options are assigned")
    fun checkOptions() {
        Assertions.assertNotNull(exServer)
        Assertions.assertNotNull(currencyAppID)
        Assertions.assertNotNull(curBase)
        Assertions.assertNotNull(gifServer)
        Assertions.assertNotNull(gifApiID)
        Assertions.assertNotNull(rich)
        Assertions.assertNotNull(broken)
    }
}