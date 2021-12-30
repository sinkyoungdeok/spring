package com.microservices.chapter4

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class CustomerController {
    @Autowired
    private lateinit var customerService: CustomerService

    @GetMapping(value = arrayOf("/customer/{id}"))
    fun getCustomer(@PathVariable id: Int): ResponseEntity<Mono<Customer>> {
        val customer = customerService.getCustomer(id)
        return ResponseEntity(customer, HttpStatus.OK)
    }

    @GetMapping(value = arrayOf("/customers"))
    fun getCustomers(@RequestParam(required = false, defaultValue = "") nameFilter: String): Flux<Customer> {
        return customerService.searchCustomers(nameFilter)
    }

    @PostMapping(value = arrayOf("/customer"))
    fun createCustomer(@RequestBody customerMono: Mono<Customer>): ResponseEntity<Mono<*>> {
        return ResponseEntity(customerService.createCustomer(customerMono), HttpStatus.CREATED)
    }
}