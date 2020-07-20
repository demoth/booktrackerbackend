package org.dnj.booktracker

import org.dnj.booktracker.repo.BookRepository
import org.dnj.booktracker.repo.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class Initializer(
    @Autowired val bookRepo: BookRepository,
    @Autowired val userRepo: UserRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val daniil = userRepo.save(User("daniil", ""))
        val julia = userRepo.save(User("julia", ""))

        bookRepo.save(BookRecord(daniil, "Тёмная башня 1"))
        bookRepo.save(BookRecord(daniil, "Властелин колец"))
        bookRepo.save(BookRecord(daniil, "Мозг материален"))
        bookRepo.save(BookRecord(julia, "Характер физических законов"))
        bookRepo.save(BookRecord(julia, "Маленький принц"))
        bookRepo.save(BookRecord(julia, "Гарри Поттер"))
    }
}