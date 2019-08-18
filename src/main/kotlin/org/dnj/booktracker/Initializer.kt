package org.dnj.booktracker

import org.dnj.booktracker.repo.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class Initializer(
    @Autowired val bookRepo: BookRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        bookRepo.save(BookRecord("Тёмная башня 1"))
        bookRepo.save(BookRecord("Властелин колец"))
        bookRepo.save(BookRecord("Мозг материален"))
        bookRepo.save(BookRecord("Характер физических законов"))
        bookRepo.save(BookRecord("Маленький принц"))
        bookRepo.save(BookRecord("Гарри Поттер"))
    }
}