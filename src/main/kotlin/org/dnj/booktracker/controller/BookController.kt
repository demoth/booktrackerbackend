package org.dnj.booktracker.controller

import org.dnj.booktracker.BookRecord
import org.dnj.booktracker.repo.BookRepository
import org.dnj.booktracker.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin
class BookController(
    @Autowired val bookRepo: BookRepository,
    @Autowired val authService: AuthService
) {

    @GetMapping("/books")
    fun getUserBooks(
        @RequestHeader("Authentication") token: String
    ): Iterable<BookRecord> {
        val user = authService.validateToken(token)
        return bookRepo.findByOwnerName(user.name)
    }

    @GetMapping("/book")
    fun getBooks(
        @RequestHeader("Authentication") token: String,
        id: String
    ): Optional<BookRecord> {
        val user = authService.validateToken(token)
        return bookRepo.findByIdAndOwnerName(id, user.name)
    }

    @PostMapping("/update_book")
    fun updateBook(
        @RequestHeader("Authentication") token: String,
        @RequestBody book: BookRecord
    ) {
        if (book.id.isNullOrBlank())
            book.id = UUID.randomUUID().toString()
        val user = authService.validateToken(token)
        book.owner = user
        bookRepo.save(book)
    }

    @PostMapping("/delete_book")
    fun deleteBook(
        @RequestHeader("Authentication") token: String,
        id: String
    ) {
        val user = authService.validateToken(token)

        bookRepo.findByIdAndOwnerName(id, user.name).ifPresent {
            bookRepo.delete(it)
        }
    }


}