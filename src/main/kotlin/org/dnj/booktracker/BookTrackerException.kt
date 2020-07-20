package org.dnj.booktracker

import org.springframework.http.HttpStatus

class BookTrackerException(override val message: String, val httpStatus: HttpStatus) : Exception(message)