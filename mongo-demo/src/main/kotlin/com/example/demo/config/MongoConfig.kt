package com.example.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.ReactiveMongoTransactionManager
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.mapping.MongoMappingContext

@Configuration(proxyBeanMethods = false)
class MongoConfig {

    @Bean
    fun reactiveMappingMongoConverter(
        mappingContext: MongoMappingContext,
    ) : MappingMongoConverter {
        val converter = MappingMongoConverter(ReactiveMongoTemplate.NO_OP_REF_RESOLVER, mappingContext)
        converter.setTypeMapper(DefaultMongoTypeMapper(null))

        return converter
    }

    @Bean
    fun transactionManager(reactiveMongoDatabaseFactory: ReactiveMongoDatabaseFactory) = ReactiveMongoTransactionManager(reactiveMongoDatabaseFactory)
}
