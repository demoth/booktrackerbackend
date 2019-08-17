package org.dnj.booktracker

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class BookRecord(
    @Id val name: String,
    var description: String = "",
    var progress: Int = 0
)