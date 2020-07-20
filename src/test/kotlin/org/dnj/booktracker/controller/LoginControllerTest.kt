package org.dnj.booktracker.controller

import org.dnj.booktracker.BookTracker
import org.dnj.booktracker.LoginRequest
import org.dnj.booktracker.User
import org.dnj.booktracker.repo.UserRepository
import org.dnj.booktracker.service.AuthService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForObject
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
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


    @Before
    fun setUp() {
        userRepository.save(TEST_USER)
    }

    @Test
    fun login() {
        val loginRequest = LoginRequest("TestAuthUser", "123")
        val response = rest.postForObject<String>("/login", loginRequest, LoginRequest::class)
        authService.validateToken("Bearer $response")
    }

}