package org.repl.springcloud.user.dao

import org.repl.springcloud.common.dto.ListViewRequestDto
import org.repl.springcloud.common.dto.PaginatedListDto
import org.repl.springcloud.user.dao.model.UserMdl
import org.repl.springcloud.user.dto.UserDto

interface UserDao {
    fun getAll(): List<UserMdl>
    fun getFilteredPaginated(requestDto: ListViewRequestDto): PaginatedListDto<UserDto>
    fun create(model: UserDto): UserDto
    fun getByUserHandle(userHandle: String): UserDto?
}