package org.repl.springcloud.common.dto

class PaginatedListDto<T> {
    var results: List<T> = ArrayList()
    var pageSize: Int = 0
    var pageNum: Int = 0
    var totalCount: Long = 0
}