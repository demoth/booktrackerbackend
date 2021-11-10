package org.dnj.booktracker.controller

import org.dnj.booktracker.BookTracker
import org.dnj.booktracker.ErrorResponse
import org.dnj.booktracker.LoginResponse
import org.dnj.booktracker.User
import org.dnj.booktracker.repo.UserRepository
import org.dnj.booktracker.service.AuthService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForObject
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = [BookTracker::class])
class UserControllerService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var rest: TestRestTemplate

    @Autowired
    lateinit var authService: AuthService

    @Test
    fun `create new user and login`() {
        val user = User("NewUser", "123")
        val response = rest.postForObject<LoginResponse>("/register", user, User::class)
        authService.validateToken("Bearer ${response!!.jwt}")
    }

    @Test
    fun `cannot create two users with the same name`() {
        val user1 = User("NewUser1", "123")
        val response1 = rest.postForObject<LoginResponse>("/register", user1, User::class)
        authService.validateToken("Bearer ${response1!!.jwt}")

        val user2 = User("NewUser1", "123")
        val response2 = rest.exchange("/register", HttpMethod.POST, HttpEntity(user2), ErrorResponse::class.java)
        assertEquals(HttpStatus.BAD_REQUEST, response2!!.statusCode)
    }
}
