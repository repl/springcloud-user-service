package org.repl.springcloud.user.dao.model

import org.repl.springcloud.user.dto.UserDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "User")
class UserMdl {
    @Id
    var id: String? = null
    @Indexed(sparse = true)
    var userHandle: String? = null
    var firstName: String? = null
    var lastName: String? = null
    @Indexed(sparse = true)
    var emailAddress: String? = null
    @Indexed(sparse = true)
    var mobile: String? = null
    var password: String? = null
    var roles: List<String>? = null
    var createdDate: Date? = null
    var createdByUID: String? = null
    var modifiedDate: Date? = null
    var modifiedByUID: String? = null

    fun createDto(): UserDto {
        val retDto = UserDto()
        retDto.id = id
        retDto.userHandle = userHandle
        retDto.firstName = firstName
        retDto.lastName = lastName
        retDto.emailAddress = emailAddress
        retDto.mobile = mobile
        retDto.password = password
        retDto.roles = roles
        retDto.createdDate = createdDate
        retDto.createdByUID = createdByUID
        retDto.modifiedDate = modifiedDate
        retDto.modifiedByUID = modifiedByUID
        return retDto
    }

    fun populate(input: UserDto) {
        this.id = input.id
        this.userHandle = input.userHandle
        this.firstName = input.firstName
        this.lastName = input.lastName
        this.emailAddress = input.emailAddress
        this.mobile = input.mobile
        this.password = input.password
        this.roles = input.roles
        this.createdDate = input.createdDate
        this.createdByUID = input.createdByUID
        this.modifiedDate = input.modifiedDate
        this.modifiedByUID = input.modifiedByUID
    }
}