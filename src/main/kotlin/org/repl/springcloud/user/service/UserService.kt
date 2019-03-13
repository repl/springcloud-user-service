package org.repl.springcloud.user.service

import org.repl.springcloud.common.dto.ServiceResponse
import org.repl.springcloud.common.dto.IdDto
import org.repl.springcloud.common.dto.ListViewRequestDto
import org.repl.springcloud.common.dto.PaginatedListDto
import org.repl.springcloud.user.dto.LoginDto
import org.repl.springcloud.user.dto.UserDto

interface UserService {
    fun getCollection(requestDto: ListViewRequestDto): ServiceResponse<PaginatedListDto<UserDto>>?
    fun create(input: UserDto): ServiceResponse<IdDto>?
    fun authenticate(loginDto: LoginDto): ServiceResponse<UserDto>?
    fun findUserByUserHandle(username: String): UserDto?
    fun getPassword(user: UserDto): String
    fun matchPassword(password: String, passwordInDb: String): Boolean
}
