package org.dnj.booktracker.service

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.dnj.booktracker.User
import org.dnj.booktracker.repo.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class AuthService(
    @Autowired val userRepository: UserRepository
) {
    /**
     * validates jwt token and returns user name
     */
    fun validateToken(token: String): User {
        val algorithm: Algorithm = Algorithm.HMAC256("secret")
        val verifier: JWTVerifier = JWT.require(algorithm)
            .withIssuer("booktracker")
            .build() //Reusable verifier instance

        val jwt: DecodedJWT = verifier.verify(token)
        val userName = jwt.getClaim("loggedInAs").asString()
        return userRepository.findById(userName).orElseGet {
            throw Exception("No such user")
        }
    }
}