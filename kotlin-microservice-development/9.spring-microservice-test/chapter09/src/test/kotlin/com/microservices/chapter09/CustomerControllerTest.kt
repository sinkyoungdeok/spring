package com.microservices.chapter09

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.amshove.kluent.*
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultHandler
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.result.JsonPathResultMatchers

class WithKeyword {
    infix fun `json path`(expression: String) = jsonPath("\$" + expression)
}

val With = WithKeyword()

class ThatKeyword {
    infix fun `status is http`(value: Int) = status().`is`(value)
}

val That = ThatKeyword()

infix fun JsonPathResultMatchers.`that the value is`(value: Any) = this.value(value)
infix fun ResultActions.`and expect`(matcher: ResultMatcher) = this.andExpect(matcher)
infix fun ResultActions.`and then do`(handler: ResultHandler) = this.andDo(handler)
infix fun MockMvc.`do a get request to`(uri: String) = this.perform(get(uri))

@AutoConfigureMockMvc
@SpringBootTest
class CustomerControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var customerService: CustomerService

    @Test
    fun `mock mvc should be configured`() {
    }

    @Test
    fun `we should GET a customer by id`() {
        When calling customerService.getCustomer(1) `it returns`
                Customer(1, "mock customer")

        (mockMvc `do a get request to` "/customer/1"
                `and expect` (That `status is http` 200)
                `and expect` (With `json path` ".id" `that the value is` 1)
                `and expect` (With `json path` ".name" `that the value is` "mock customer")
                ) `and then do` print()


        Verify on customerService that customerService.getCustomer(1) was called
        `Verify no further interactions` on customerService
    }

    @Test
    fun `we should GET a list of customers`() {
        When calling customerService.getAllCustomers() `it returns`
                listOf(Customer(1, "test"), Customer(2, "mocks"))

        mockMvc.perform(get("/customers"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("\$").isArray)
            .andExpect(jsonPath("\$[0].id").value(1))
            .andExpect(jsonPath("\$[0].name").value("test"))
            .andExpect(jsonPath("\$[1].id").value(2))
            .andExpect(jsonPath("\$[1].name").value("mocks"))
//            .andExpect(jsonPath("\$[2].id").value(3))
//            .andExpect(jsonPath("\$[2].name").value("Mocriservice"))
            .andDo(print())

        then(customerService).should(times(1)).getAllCustomers()
        then(customerService).shouldHaveNoMoreInteractions()
    }
}