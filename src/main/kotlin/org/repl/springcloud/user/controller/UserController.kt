package org.repl.springcloud.user.controller

import org.apache.commons.lang3.StringUtils
import org.repl.springcloud.common.dto.ServiceResponse
import org.repl.springcloud.common.dto.IdDto
import org.repl.springcloud.common.dto.ListViewRequestDto
import org.repl.springcloud.common.dto.PaginatedListDto
import org.repl.springcloud.common.exception.ServiceException
import org.repl.springcloud.user.dto.LoginDto
import org.repl.springcloud.user.dto.UserDto
import org.repl.springcloud.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController() {

    @Autowired
    lateinit var userService: UserService

    @RequestMapping(method = [RequestMethod.GET], headers = ["Accept=application/json"])
    @ResponseBody
    fun getCollection(@RequestParam("pageSize", required = false) pageSize : String?,
                      @RequestParam(value = "pageNum", required = false) pageNum : String?,
                      @RequestParam(value = "sortKey", required = false) sortKey : String?,
                      @RequestParam(value = "sortOrder", required = false, defaultValue = "asc") sortOrder : String,
                      @RequestParam(value = "rangeKey", required = false) filterRangeKey : String?,
                      @RequestParam(value = "rangeOp", required = false) filterRangeOp : String?,
                      @RequestParam(value = "rangeValue", required = false) filterRangeValue : String?,
                      @RequestParam(value = "mobile", required = false) filterMobile : String?,
                      @RequestParam(value = "firstName", required = false) filterFirstName : String?,
                      @RequestParam(value = "lastName", required = false) filterLastName : String?,
                      @RequestParam(value = "emailAddress", required = false) filterEmail : String?): ServiceResponse<PaginatedListDto<UserDto>>? {
        val requestDto = ListViewRequestDto()
        val filtersMap = requestDto.filters
        if (!StringUtils.isEmpty(filterRangeKey)) {
            filtersMap.put("rangeKey", filterRangeKey.toString());
        }
        if (!StringUtils.isEmpty(filterRangeOp)) {
            filtersMap.put("rangeOp", filterRangeOp.toString())
        }
        if (!StringUtils.isEmpty(filterRangeValue)) {
            filtersMap.put("rangeValue", filterRangeValue.toString())
        }
        if (!StringUtils.isEmpty(pageSize)) {
            try {
                requestDto.pageSize = Integer.parseInt(pageSize)
            } catch (ex: NumberFormatException) {
                throw ServiceException("Not able to parse pageSize value.")
            }

        }
        if (!StringUtils.isEmpty(pageNum)) {
            try {
                requestDto.pageNum = Integer.parseInt(pageNum)
            } catch (ex: NumberFormatException) {
                throw ServiceException("Not able to parse pageNum value.")
            }

        }
        if (!StringUtils.isEmpty(sortKey)) {
            if (!StringUtils.isEmpty(sortOrder)) {
                requestDto.sortMap.put(sortKey.toString(), sortOrder)
            } else {
                requestDto.sortMap.put(sortKey.toString(), "asc")
            }
        }
        if (StringUtils.isNotEmpty(filterMobile)) {
            filtersMap.put("mobile", filterMobile.toString())
        }
        if (StringUtils.isNotEmpty(filterFirstName)) {
            filtersMap.put("firstName", filterFirstName.toString().toLowerCase())
        }
        if (StringUtils.isNotEmpty(filterLastName)) {
            filtersMap.put("lastName", filterLastName.toString().toLowerCase())
        }
        if (StringUtils.isNotEmpty(filterEmail)) {
            filtersMap.put("emailAddress", filterEmail.toString())
        }
        return userService.getCollection(requestDto)
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun createUsers(@RequestBody input: UserDto): ServiceResponse<IdDto>? {
        return userService.create(input)
    }

    @PostMapping(path = ["/login"], consumes = [MediaType.APPLICATION_JSON_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun authenticate(@RequestBody input: LoginDto): ServiceResponse<UserDto>? {
        return userService.authenticate(input)
    }
}