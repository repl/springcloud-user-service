package org.repl.springcloud.user.dao

import org.repl.springcloud.user.dao.model.UserMdl
import org.springframework.data.repository.Repository
import java.util.*

interface UserRepository : Repository<UserMdl, String> {
    fun delete(model: UserMdl)
    fun findAll(): List<UserMdl>
    fun findOne(id: String): Optional<UserMdl>
    fun save(model: UserMdl): UserMdl
}