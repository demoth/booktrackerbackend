package org.dnj.booktracker

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class BookRecord(
        @ManyToOne var owner: User?,
        var name: String,
        @Id var id: String = UUID.randomUUID().toString(),
        var description: String = "",
        var progress: Int = 0
)

@Entity
data class User(
        @Id var name: String,
        var password: String
)

data class LoginRequest(
        val name: String,
        val password: String
)

data class LoginResponse(
        val jwt: String
)

data class ErrorResponse(
        val message: String
)