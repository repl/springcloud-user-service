package org.repl.springcloud.user.dto

import java.util.*

open class Item {
    var id: String? = null
    var createdDate: Date? = null
    var createdByUID: String? = null
    var modifiedDate: Date? = null
    var modifiedByUID: String? = null
}