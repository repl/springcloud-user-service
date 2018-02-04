package org.repl.springcloud.user.dto

class UserDto : Item() {
    var userHandle: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var emailAddress: String? = null
    var mobile: String? = null
    var password: String? = null
    var roles: List<String>? = null
}