package org.dnj.booktracker.controller

import org.dnj.booktracker.BookTracker
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = [BookTracker::class])
class HealthControllerTest {

    @Autowired
    lateinit var rest: TestRestTemplate

    @Test
    fun testHealth() {
        val response = rest.getForObject("/health", String::class.java)
        assertEquals("ok", response)
    }
}
