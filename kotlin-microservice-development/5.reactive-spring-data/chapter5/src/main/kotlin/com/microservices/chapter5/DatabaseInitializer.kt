package com.microservices.chapter5

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class DatabaseInitializer {
    @Autowired
    lateinit var mongoOperations: ReactiveMongoOperations

    @PostConstruct
    fun initData() {
        mongoOperations.collectionExists("Customers").subscribe{ if (it != true)
            mongoOperations.createCollection("Customers").subscribe{
                println("Customers collections created")
            } else println("Customers collections already exist")
        }

    }
}