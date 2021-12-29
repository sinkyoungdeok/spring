package com.microservices.chapter3

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.concurrent.ConcurrentHashMap

@RestController
class CustomerController {
    @Autowired
    lateinit var customers : ConcurrentHashMap<Int, Customer>

    @RequestMapping(value = arrayOf("/customers"), method = arrayOf(RequestMethod.GET))
    fun getCustomers(@RequestParam(required = false, defaultValue = "") nameFilter: String) =
        customers.map(Map.Entry<Int,Customer>::value).toString()

    @RequestMapping(value = arrayOf("/customer/{id}"), method = arrayOf(RequestMethod.GET))
    fun getCustomer(@PathVariable id: Int) = customers[id]

    @RequestMapping(value = arrayOf("/customer"), method = arrayOf(RequestMethod.POST))
    fun createCustomer(@RequestBody customer: Customer) {
        customers[customer.id] = customer
    }
}