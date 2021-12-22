package com.example.mvc.model.http

import com.example.mvc.controller.annotation.StringFormatDateTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.validation.constraints.*


data class UserRequest(

    @field:Size(min=2,max=8)
    @field:NotEmpty
    var name:String?=null,

    @field:PositiveOrZero
    var age:Int?=null,

    @field:Email
    var email:String?=null,

    @field:NotBlank
    var address:String?=null,

    @field:Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}\$")
    var phoneNumber:String?=null,

    @field:StringFormatDateTime(pattern = "yyyy-MM-dd HH:mm:ss", message = "패턴이 올바르지 않습니다.")
    var createdAt: String?=null
) {

}
