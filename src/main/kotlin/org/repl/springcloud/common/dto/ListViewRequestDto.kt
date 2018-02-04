package org.repl.springcloud.common.dto

class ListViewRequestDto {
    var filters: MutableMap<String, Any> = HashMap()
    var pageSize: Int = 0
    var pageNum: Int = 0
    var sortMap: MutableMap<String, String> = HashMap()
}