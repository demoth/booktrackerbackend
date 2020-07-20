package org.dnj.booktracker.controller

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.dnj.booktracker.repo.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class LoginController(
    @Autowired val userRepository: UserRepository
) {

    private val CLAIM_NAME = "loggedInAs"

    @PostMapping("/login")
    fun login(
        /*@RequestParam("name")*/ name: String,
        /*@RequestParam("password")*/ password: String
    ): String {
        val user = userRepository.findById(name).orElseThrow {
            RuntimeException("No such user")
        }

        if (user.password != password) {
            throw RuntimeException("Wrong password")
        }

        val algorithm: Algorithm = Algorithm.HMAC256("secret")
        val token = JWT.create()
            .withIssuer("booktracker")
            .withClaim(CLAIM_NAME, user.name)
            .sign(algorithm)
        return token;
    }

}