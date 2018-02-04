package org.repl.springcloud.common.dto

import java.util.ArrayList

class ListViewDto<T> {
    private val results : List<T> = ArrayList()
    private val totalCount: Int = 0
}