package com.microservices.chapter4

import com.microservices.chapter4.Customer
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono
import reactor.core.publisher.onErrorResume
import reactor.core.publisher.switchIfEmpty
import java.net.URI

@Component
class CustomerHandler(val customerService: CustomerService) {
    fun get(serverRequst: ServerRequest) =
        customerService.getCustomer(serverRequst.pathVariable("id").toInt())
            .flatMap { ok().body(fromObject(it)) }
            .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())

    fun search(serverRequest: ServerRequest) = ok().body(
        customerService.searchCustomers(serverRequest.queryParam("nameFilter").orElse("")),
        Customer::class.java
    )

    fun create(serverRequest: ServerRequest): Mono<ServerResponse> =
        customerService.createCustomer(serverRequest.bodyToMono())
            .flatMap { created(URI.create("/functional/customer/${it.id}")).build() }
            .onErrorResume(Exception::class) {
                badRequest().body(fromObject(ErrorResponse("error creating customer", it.message ?: "error")))
            }

}