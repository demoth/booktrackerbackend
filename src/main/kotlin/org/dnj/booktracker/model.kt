package org.dnj.booktracker

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class BookRecord(
    var name: String,
    @Id var id: String = UUID.randomUUID().toString(),
    var description: String = "",
    var progress: Int = 0
)