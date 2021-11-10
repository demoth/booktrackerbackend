package org.dnj.booktracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BookTracker

fun main(args: Array<String>) {
    runApplication<BookTracker>(*args)
}