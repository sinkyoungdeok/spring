package com.microservices.chapter3

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.concurrent.ConcurrentHashMap

@RestController
class CustomerController {
    @Autowired
    lateinit var customers : ConcurrentHashMap<Int, Customer>

    @GetMapping(value = arrayOf("/customers"))
    fun getCustomers(@RequestParam(required = false, defaultValue = "") nameFilter: String) =
        customers.map(Map.Entry<Int,Customer>::value).toString()

    @GetMapping(value = arrayOf("/customer/{id}") )
    fun getCustomer(@PathVariable id: Int) = customers[id]

    @PostMapping(value = arrayOf("/customer"))
    fun createCustomer(@RequestBody customer: Customer) {
        customers[customer.id] = customer
    }

    @DeleteMapping(value = arrayOf("/customer/{id}"))
    fun deleteCustomer(@PathVariable id: Int) = customers.remove(id)

    @PutMapping(value = arrayOf("/customer/{id}"))
    fun updateCustomer(@PathVariable id: Int, @RequestBody customer: Customer) {
        customers.remove(id)
        customers[customer.id] = customer
    }
}