package com.microservices.chapter5

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse.status

@Component
class CustomerHandler(val customerService: CustomerService) {
    fun get(serverRequest: ServerRequest) =
        customerService.getCustomer(serverRequest.pathVariable("id").toInt())
            .flatMap { ok().body(fromObject(it)) }
            .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())
}