package org.repl.springcloud.common.dto

public class ServiceResponse<T> {
    var success: Boolean = false
    val errors : MutableList<ServiceResponseError> = mutableListOf()
    var data: T? = null
}