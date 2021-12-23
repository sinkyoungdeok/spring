package com.example.todo.model.http

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.validation.constraints.AssertTrue
import javax.validation.constraints.NotBlank

data class TodoDto (
    var index:Int?=null,

    @field:NotBlank
    var title:String?=null,

    var description:String ?=null,

    @field:NotBlank
    var schedule: String?=null,
    var createdAt: LocalDateTime?=null,
    var updatedAt: LocalDateTime?=null
){
    @AssertTrue(message = "yyyy-MM-dd HH:mm:ss 포맷이 맞지 않습니다. ")
    fun validSchedule(): Boolean {
        return try {
            LocalDateTime.parse(schedule, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            true
        } catch (e : Exception) {
            false
        }
    }
}