package org.dnj.booktracker.controller

import org.dnj.booktracker.BookRecord
import org.dnj.booktracker.repo.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController(
    @Autowired val bookRepo: BookRepository
) {

    @GetMapping("/books")
    fun getBooks(): Iterable<BookRecord> {
        return bookRepo.findAll()
    }

    @PostMapping("/update_book")
    fun updateBook(@RequestBody book: BookRecord) {
        bookRepo.save(book)
    }

    @PostMapping("/delete_book")
    fun deleteBook(name: String) {
        bookRepo.findById(name).ifPresent {
            bookRepo.delete(it)
        }
    }


}