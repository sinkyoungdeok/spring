package com.example.mvc.model.http

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

data class UserResponse(

    var result:Result?=null,
    var description:String?=null,
    @JsonProperty("user")
    var userRequest: MutableList<UserRequest>?=null
)

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class Result(
    var resultCode: String?=null,
    var resultMessage: String?=null
)