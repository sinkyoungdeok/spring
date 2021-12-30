package com.microservices.chapter4

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class CustomerHandler {
    fun get(serverRequst: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok().body(Customer(1, "functional web").toMono(), Customer::class.java)
    }
}