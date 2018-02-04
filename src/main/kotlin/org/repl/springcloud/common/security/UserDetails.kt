package org.repl.springcloud.common.security

class UserDetails {
    var id: String? = null
        private set
    var userName: String? = null
        private set
    var clientId: String? = null
        private set

    constructor(id: String, userName: String, clientId: String) {
        this.id = id
        this.userName = userName
        this.clientId = clientId
    }

    constructor(id: String, userName: String) {
        this.id = id
        this.userName = userName
        this.clientId = "na"
    }

    override fun toString(): String {
        return "UserDetails{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", clientId='" + clientId + '\'' +
                '}'
    }
}