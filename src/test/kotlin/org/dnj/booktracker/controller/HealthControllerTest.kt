package org.dnj.booktracker.controller

import org.dnj.booktracker.BookTracker
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
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
