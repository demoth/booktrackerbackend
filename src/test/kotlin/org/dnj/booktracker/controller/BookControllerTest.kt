package org.dnj.booktracker.controller

import org.dnj.booktracker.BookRecord
import org.dnj.booktracker.BookTracker
import org.dnj.booktracker.repo.BookRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForObject
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = [BookTracker::class])

class BookControllerTest {
    private val TEST_ID = "id1"

    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var rest: TestRestTemplate

    val TEST_BOOK = BookRecord("TestBook", TEST_ID, "No meaning", 42)

    @Before
    fun setUp() {
        bookRepository.deleteAll()
        bookRepository.save(TEST_BOOK)
    }

    @Test
    fun getBooks() {
        val response = rest.getForObject("/books", Array<BookRecord>::class.java)
        assertArrayEquals(arrayOf(TEST_BOOK), response)
    }

    @Test
    fun deleteBook() {
        rest.postForObject<String>("/delete_book?id=$TEST_ID", String::class.java)
        assertTrue(bookRepository.findAll().toList().isEmpty())
    }

    @Test
    fun getBook() {
        val response = rest.getForObject("/book?id=$TEST_ID", BookRecord::class.java)
        assertEquals(TEST_BOOK, response)
    }

    @Test
    fun updateBook() {
        val request = TEST_BOOK.copy(description = "updated_description")
        rest.postForObject("/update_book", request, String::class.java)
        assertEquals("updated_description", bookRepository.findById(TEST_ID).get().description)

    }
}