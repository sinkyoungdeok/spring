package com.example.todo.model.http

import com.example.todo.database.Todo
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

fun TodoDto.convertTodoDto(todo: Todo): TodoDto {
    return TodoDto().apply {
        this.index = todo.index
        this.title = todo.title
        this.description = todo.description
        this.schedule = todo.schedule?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        this.createdAt = todo.createdAt
        this.updatedAt = todo.updatedAt
    }
}