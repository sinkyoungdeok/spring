package com.microservices.chapter06discoveryserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class Chapter06DiscoveryserverApplication

fun main(args: Array<String>) {
	runApplication<Chapter06DiscoveryserverApplication>(*args)
}
