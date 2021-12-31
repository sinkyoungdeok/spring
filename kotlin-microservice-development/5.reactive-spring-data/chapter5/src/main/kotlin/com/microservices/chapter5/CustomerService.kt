package com.microservices.chapter5

import reactor.core.publisher.Mono

interface CustomerService {
    fun getCustomer(id: Int): Mono<Customer>
}