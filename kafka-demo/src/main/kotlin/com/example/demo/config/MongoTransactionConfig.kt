//package com.example.demo.config
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.data.mongodb.MongoDatabaseFactory
//import org.springframework.data.mongodb.MongoTransactionManager
//import org.springframework.transaction.annotation.EnableTransactionManagement
//
//@EnableTransactionManagement
//@Configuration(proxyBeanMethods = false)
//class MongoTransactionConfig {
//
//    @Bean
//    fun transactionManager(dbFactory: MongoDatabaseFactory): MongoTransactionManager {
//        return MongoTransactionManager(dbFactory)
//    }
//}
