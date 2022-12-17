package org.dnj.booktracker.controller

import org.dnj.booktracker.*
import org.dnj.booktracker.repo.BookRepository
import org.dnj.booktracker.repo.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForObject
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension


@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = [BookTracker::class])
class BookRecordControllerTest {
    private val TEST_ID = "id1"

    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var rest: TestRestTemplate

    var TEST_USER = User("TestUser", "321")

    var TEST_BOOK = BookRecord(TEST_USER, "TestBook", TEST_ID, "No meaning", 42)

    @BeforeEach
    fun setUp() {
        bookRepository.deleteAll()
        userRepository.deleteAll()

        userRepository.save(TEST_USER)
        bookRepository.save(TEST_BOOK)
    }

    @Test
    fun `get books positive`() {
        val entity = HttpEntity<Array<BookRecord>>(prepareAuthHeaders())
        val response = rest.exchange(
            "/books",
            HttpMethod.GET,
            entity,
            Array<BookRecord>::class.java
        )
        assertArrayEquals(arrayOf(TEST_BOOK), response.body)
    }

    @Test
    fun `delete book`() {
        val entity = HttpEntity<String>(prepareAuthHeaders())

        rest.exchange(
            "/delete_book?id=$TEST_ID",
            HttpMethod.POST,
            entity,
            String::class.java
        )

        assertTrue(bookRepository.findAll().toList().isEmpty())

    }

    @Test
    fun `get book positive`() {
        val entity = HttpEntity<BookRecord>(prepareAuthHeaders())
        val response = rest.exchange(
            "/book?id=$TEST_ID",
            HttpMethod.GET,
            entity,
            BookRecord::class.java
        )
        assertEquals(TEST_BOOK, response.body)
    }

    @Test
    fun `update book`() {
        val request = TEST_BOOK.copy(description = "updated_description")
        rest.postForObject("/update_book", request, String::class.java)

        val entity = HttpEntity(request, prepareAuthHeaders())

        rest.exchange(
            "/update_book",
            HttpMethod.POST,
            entity,
            String::class.java
        )

        assertEquals("updated_description", bookRepository.findById(TEST_ID).get().description)

    }

    @Test
    fun `create new book`() {
        val request = BookRecord(TEST_USER, "new book", "")
        rest.postForObject("/update_book", request, String::class.java)

        val entity = HttpEntity(request, prepareAuthHeaders())

        rest.exchange(
            "/update_book",
            HttpMethod.POST,
            entity,
            String::class.java
        )

        assertNotNull(bookRepository.findByOwnerName(TEST_USER.username).find {
            it.name == "new book"
        })

    }

    private fun prepareAuthHeaders(): HttpHeaders {
        val loginRequest = LoginRequest(TEST_USER.username, TEST_USER.password)
        val loginResponse = rest.postForObject<LoginResponse>("/login", loginRequest, LoginRequest::class)
        val headers = HttpHeaders()
        headers.set("Authentication", "Bearer ${loginResponse!!.jwt}")
        return headers
    }

}
