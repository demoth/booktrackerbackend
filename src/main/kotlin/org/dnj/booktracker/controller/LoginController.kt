package org.dnj.booktracker.controller

import org.dnj.booktracker.LoginRequest
import org.dnj.booktracker.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class LoginController(@Autowired val authService: AuthService) {

    @PostMapping("/login")
    fun login(@RequestBody login: LoginRequest): String {
        return authService.loginUser(login.name, login.password)
    }

}
