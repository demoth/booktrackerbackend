package org.dnj.booktracker.controller

import org.dnj.booktracker.BookRecord
import org.dnj.booktracker.repo.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin
class BookController(
    @Autowired val bookRepo: BookRepository
) {

    @GetMapping("/books")
    fun getBooks(): Iterable<BookRecord> {
        return bookRepo.findAll()
    }

    @GetMapping("/book")
    fun getBooks(id: String): Optional<BookRecord> {
        return bookRepo.findById(id)
    }

    @PostMapping("/update_book")
    fun updateBook(@RequestBody book: BookRecord) {
        if (book.id.isNullOrBlank())
            book.id = UUID.randomUUID().toString()
        bookRepo.save(book)
    }

    @PostMapping("/delete_book")
    fun deleteBook(id: String) {
        bookRepo.findById(id).ifPresent {
            bookRepo.delete(it)
        }
    }


}