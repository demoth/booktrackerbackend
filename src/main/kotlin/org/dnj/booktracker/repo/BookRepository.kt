package org.dnj.booktracker.repo

import org.dnj.booktracker.BookRecord
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : CrudRepository<BookRecord, String> {

}