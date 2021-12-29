package com.microservices.chapter2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Chapter2Application

fun main(args: Array<String>) {
	runApplication<Chapter2Application>(*args)
}
