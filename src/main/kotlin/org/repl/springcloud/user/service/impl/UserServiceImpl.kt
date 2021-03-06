package org.repl.springcloud.user.service.impl

import org.repl.springcloud.common.dto.ServiceResponse
import org.repl.springcloud.common.dto.ServiceResponseError
import org.repl.springcloud.common.dto.IdDto
import org.repl.springcloud.common.dto.ListViewRequestDto
import org.repl.springcloud.common.dto.PaginatedListDto
import org.repl.springcloud.common.exception.ServiceException
import org.repl.springcloud.user.dao.UserDao
import org.repl.springcloud.user.dto.LoginDto
import org.repl.springcloud.user.dto.UserDto
import org.repl.springcloud.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl : UserService {

    @Autowired
    private lateinit var userDao: UserDao

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    fun getAll(): ServiceResponse<Iterable<UserDto>>? {
        val response = ServiceResponse<Iterable<UserDto>>()
        response.data = userDao.getAll().map { userMdl -> userMdl.createDto() }
        response.success = true
        return response
    }

    override fun getCollection(requestDto: ListViewRequestDto): ServiceResponse<PaginatedListDto<UserDto>>? {
        val response = ServiceResponse<PaginatedListDto<UserDto>>()
        val fetchPaginatedDto = userDao.getFilteredPaginated(requestDto)
        val fetchedList = fetchPaginatedDto.results;
        val revisedDtoList = fetchedList.map { dto ->
            dto.password = "xxxxxxxx"
            dto
        }
        //val revisedDtoList = buildSequence {
        //    for (i in 0 until fetchedList.size) {
        //        fetchedList[i].password = "xxxxxxx"
        //        yield(fetchedList[i])
        //    }
        //}.toList();
        fetchPaginatedDto.results = revisedDtoList
        response.data = fetchPaginatedDto;
        response.success = true
        return response
    }

    override fun create(input: UserDto): ServiceResponse<IdDto>? {
        val response = ServiceResponse<IdDto>()
        val errors = validateCreateInput(input)
        if (errors.isNotEmpty()) {
            response.errors.addAll(errors)
            response.success = false
            return response
        }
        input.id = UUID.randomUUID().toString()
        input.createdDate = Date()
        input.modifiedDate = Date()
        input.password = passwordEncoder.encode(input.password)
        val persistedDto = userDao.create(input)
        response.success = true
        val retDto = IdDto()
        retDto.id = persistedDto.id
        response.data = retDto
        return response
    }

    private fun validateCreateInput(input: UserDto): List<ServiceResponseError> {
        val errors: MutableList<ServiceResponseError> = mutableListOf<ServiceResponseError>()
        if (userDao.getByUserHandle(input.userHandle!!) != null) {
            errors.add(ServiceResponseError("user account with userHandle ${input.userHandle} already exists."))
        }
        if (userDao.getByMobile(input.mobile!!) != null) {
            errors.add(ServiceResponseError("user account with mobile ${input.mobile} already exists."))
        }
        if (userDao.getByEmailAddress(input.emailAddress!!) != null) {
            errors.add(ServiceResponseError("user account with email address ${input.emailAddress} already exists."))
        }
        return errors
    }

    override fun authenticate(loginDto: LoginDto): ServiceResponse<UserDto>? {
        val serviceResponse = ServiceResponse<UserDto>()
        val userDto = userDao.getByUserHandle(loginDto.userHandle)
        if (userDto == null) {
            serviceResponse.success = false
            serviceResponse.errors.add(ServiceResponseError("User record does not exist in the system."))
        } else {
            serviceResponse.success = true
            userDto.password = "xxxxxxx"
            serviceResponse.data = userDto
        }
        return serviceResponse
    }

    override fun findUserByUserHandle(username: String): UserDto? {
        val userDto = userDao.getByUserHandle(username);
        if (userDto != null) {
            userDto.password = "xxxxxxx"
        }
        return userDto
    }

    override fun getPassword(user: UserDto): String {
        val userDto = userDao.getByUserHandle(user.userHandle!!) ?: throw ServiceException("user record not found")
        return userDto.password!!
    }

    override fun matchPassword(password: String, passwordInDb: String): Boolean {
        return password == passwordInDb
    }

}