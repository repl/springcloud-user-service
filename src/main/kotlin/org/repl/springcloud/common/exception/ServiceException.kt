package org.repl.springcloud.common.exception

import java.util.ArrayList

class ServiceException : Exception {
    private var businessErrorMessages: MutableList<String> = ArrayList()

    constructor(message: String): super(message) {
        businessErrorMessages.add(message)
    }

    constructor(message: String, e: Exception): super(message, e) {
        businessErrorMessages.add(message)
    }

    fun getBusinessErrorMessages(): List<String> {
        return businessErrorMessages
    }

    fun setBusinessErrorMessages(businessErrorMessages: MutableList<String>) {
        this.businessErrorMessages = businessErrorMessages
    }

    fun addBusinessErrorMessage(message: String) {
        businessErrorMessages.add(message)
    }

    override fun toString(): String {
        return ("ServiceException{" + "message=" + message + ", businessErrorMessages=" + businessErrorMessages
                + '}')
    }
}