package io.github.chayanforyou.findmeds.payload

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BaseResponse(val success: Boolean, val message: String?, val data: Any?) {
    constructor(success: Boolean, message: String) : this(success, message, null)
    constructor(success: Boolean, data: Any) : this(success, null, data)
}
