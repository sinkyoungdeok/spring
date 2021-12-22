package com.example.mvc.controller.delete

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class DeleteApiController {

    @DeleteMapping(path = ["/delete-mapping"])
    fun deleteMapping(
        @RequestParam name: String,
        @RequestParam age: Int
    ): String {
        println(name)
        println(age)
        return name + " " + age
    }

    @DeleteMapping(path = ["/delete-mapping/{name}/{age}"])
    fun deleteMappingPath(@PathVariable name: String, @PathVariable age: Int): String {
        println(name)
        println(age)
        return name + " " + age
    }
}