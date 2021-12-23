package com.example.todo.database

import java.time.LocalDateTime

data class Todo(
    var index: Int?= null,
    var title: String?=null,
    var name: String?= null,
    var description: String?= null,
    var schedule: LocalDateTime?=null,
    var createdAt: LocalDateTime?=null,
    var updatedAt: LocalDateTime?=null
)
