package org.dnj.booktracker

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class BookTrackerExceptionHandler {
    @ExceptionHandler(BookTrackerException::class)
    fun handleAllExceptions(e: BookTrackerException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(e.message), e.httpStatus)
    }
}