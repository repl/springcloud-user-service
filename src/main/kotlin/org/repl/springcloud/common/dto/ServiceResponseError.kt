package org.repl.springcloud.common.dto

public class ServiceResponseError {
    lateinit var code: String
    lateinit var text: String

    constructor(text: String) {
        this.text = text
        this.code = "GEN0001"
    }
}