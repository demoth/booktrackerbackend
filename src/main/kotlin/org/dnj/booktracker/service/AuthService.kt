package org.dnj.booktracker.service

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import org.dnj.booktracker.BookTrackerException
import org.dnj.booktracker.LoginResponse
import org.dnj.booktracker.User
import org.dnj.booktracker.repo.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class AuthService(@Autowired private val userRepository: UserRepository) {
    private val CLAIM_NAME = "loggedInAs"

    private val ISSUER = "booktracker"

    private val JWT_SECRET = System.getenv("JWT_SECRET")

    private val VERIFIER: JWTVerifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).withIssuer(ISSUER).build()

    /**
     * validates jwt token and returns user associated with this token
     */
    fun validateToken(authHeader: String): User {
        val (type, token) = authHeader.split(" ")
        if (type != "Bearer")
            throw BookTrackerException("Authentication $type is not supproted", HttpStatus.BAD_REQUEST)
        val userName: String = try {
            VERIFIER.verify(token).getClaim(CLAIM_NAME).asString()
        } catch (e: Exception) {
            throw BookTrackerException("Invalid token", HttpStatus.UNAUTHORIZED)
        }
        return userRepository.findById(userName).orElseGet {
            throw BookTrackerException("No such user", HttpStatus.NOT_FOUND)
        }
    }

    /**
     * @return token after successful identification and authentication
     */
    fun loginUser(name: String, password: String): LoginResponse {
        val user = userRepository.findById(name).orElseThrow {
            throw BookTrackerException("No such user", HttpStatus.NOT_FOUND)
        }

        if (user.password != password) {
            throw BookTrackerException("Wrong password", HttpStatus.UNAUTHORIZED)
        }

        try {
            return LoginResponse(JWT.create()
                .withIssuer(ISSUER)
                .withClaim(CLAIM_NAME, user.name)
                .sign(Algorithm.HMAC256(JWT_SECRET)))
        } catch (e: Exception) {
            throw BookTrackerException("Could not authenticate", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
