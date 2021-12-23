package com.example.todo.database

data class TodoDataBase(
    var index: Int =0,
    var todoList: MutableList<Todo> = mutableListOf()
){

    fun init() {
        this.index = 0
        this.todoList = mutableListOf()
        println("[DEBUG] todo database init")
    }
}
