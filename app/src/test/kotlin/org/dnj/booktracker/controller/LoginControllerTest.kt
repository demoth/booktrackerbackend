package org.dnj.booktracker.controller

import org.dnj.booktracker.*
import org.dnj.booktracker.repo.UserRepository
import org.dnj.booktracker.service.AuthService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
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
class LoginControllerTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var rest: TestRestTemplate

    @Autowired
    lateinit var authService: AuthService

    var TEST_USER = User("TestAuthUser", "123")

    @BeforeEach
    fun setUp() {
        userRepository.save(TEST_USER)
    }

    @Test
    fun login() {
        val loginRequest = LoginRequest(TEST_USER.username, TEST_USER.password)
        val response = rest.postForObject<LoginResponse>("/login", loginRequest, LoginRequest::class)
        authService.validateToken("Bearer ${response!!.jwt}")
    }

    @Test
    fun `wrong password`() {
        val loginRequest = LoginRequest(TEST_USER.username, "wrong password")
        val response2 = rest.exchange("/login", HttpMethod.POST, HttpEntity(loginRequest), ErrorResponse::class.java)
        assertEquals(HttpStatus.UNAUTHORIZED, response2!!.statusCode)
    }

    @Test
    fun `login user does not exist`() {
        val loginRequest = LoginRequest("no such user", "123")
        val response2 = rest.exchange("/login", HttpMethod.POST, HttpEntity(loginRequest), ErrorResponse::class.java)
        assertEquals(HttpStatus.NOT_FOUND, response2!!.statusCode)

    }

}
