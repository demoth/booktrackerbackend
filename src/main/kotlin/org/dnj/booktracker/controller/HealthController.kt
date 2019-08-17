package org.dnj.booktracker.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {
    @GetMapping
    fun checkHealth() = ResponseEntity("Ok", HttpStatus.OK)
}