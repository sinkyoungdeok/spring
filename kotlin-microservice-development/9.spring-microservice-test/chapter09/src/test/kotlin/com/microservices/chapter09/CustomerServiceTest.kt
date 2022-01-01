package com.microservices.chapter09

import org.amshove.kluent.`should be`
import org.amshove.kluent.`should equal to`
import org.amshove.kluent.`should not be null`
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.amshove.kluent.shouldNotBeNull

@SpringBootTest
class CustomerServiceTest {
    @Autowired
    lateinit var customerService: CustomerService

    @Test
    fun `we should get a customer with a valid id`() {
        val customer = customerService.getCustomer(1)

        customer.`should not be null`()
        customer?.name `should be` "Kotlin"
    }

    @Test
    fun `we should get all customers`() {
        val customers = customerService.getAllCustomers()
        customers.size `should equal to` 3
    }
}