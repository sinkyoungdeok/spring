package com.example.mvc.controller.delete

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@RestController
@RequestMapping("/api")
@Validated
class DeleteApiController {

    @DeleteMapping(path = ["/delete-mapping"])
    fun deleteMapping(
        @RequestParam name: String,

        @NotNull(message = "age 값이 누락되었습니다.")
        @Min(value = 20, message = "age는 20보다 커야 합니다.")
        @RequestParam age: Int
    ): String {
        println(name)
        println(age)
        return name + " " + age
    }

    @DeleteMapping(path = ["/delete-mapping/{name}/{age}"])
    fun deleteMappingPath(
        @PathVariable
        @Size(min = 2, max = 5, message = "name의 길이는 2~5")
        @NotNull
        name: String,


        @NotNull(message = "age 값이 누락되었습니다.")
        @Min(value = 20, message = "age는 20보다 커야 합니다.")
        @PathVariable age: Int): String {
        println(name)
        println(age)
        return name + " " + age
    }
}