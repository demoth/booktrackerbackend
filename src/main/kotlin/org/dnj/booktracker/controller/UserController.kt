package org.dnj.booktracker.controller

import org.dnj.booktracker.BookTrackerException
import org.dnj.booktracker.User
import org.dnj.booktracker.repo.UserRepository
import org.dnj.booktracker.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class UserController(
    @Autowired val userRepository: UserRepository,
    @Autowired val authService: AuthService
) {
    @PostMapping("/register")
    fun registerNewUser(
        @RequestBody user: User
    ): String {
        if (userRepository.findById(user.name).isPresent)
            throw BookTrackerException("This name is taken", HttpStatus.BAD_REQUEST)
        userRepository.save(user)
        return authService.loginUser(user.name, user.password)
    }
}