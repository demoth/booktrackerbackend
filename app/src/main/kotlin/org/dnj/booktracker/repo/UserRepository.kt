package org.dnj.booktracker.repo

import org.dnj.booktracker.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, String> {

}