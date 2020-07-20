package org.dnj.booktracker.service

import org.dnj.booktracker.repo.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired userRepository: UserRepository
) {
    fun checkUser(token: String) {

    }
}