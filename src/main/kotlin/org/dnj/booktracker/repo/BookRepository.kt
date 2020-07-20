package org.dnj.booktracker.repo

import org.dnj.booktracker.BookRecord
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BookRepository : CrudRepository<BookRecord, String> {
    fun findByOwnerName(name: String): Iterable<BookRecord>

    fun findByIdAndOwnerName(id: String, name: String): Optional<BookRecord>
}