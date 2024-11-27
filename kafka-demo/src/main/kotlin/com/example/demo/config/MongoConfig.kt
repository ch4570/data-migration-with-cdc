package com.example.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.mapping.MongoMappingContext

@Configuration(proxyBeanMethods = false)
class MongoConfig {

    @Bean
    fun mappingMongoConverter(
        mongoDatabaseFactory: MongoDatabaseFactory,
        mongoMappingContext: MongoMappingContext,
    ) : MappingMongoConverter {
        val dbRefResolver = DefaultDbRefResolver(mongoDatabaseFactory)
        val mappingMongoConverter = MappingMongoConverter(dbRefResolver, mongoMappingContext)

        mappingMongoConverter.setTypeMapper(DefaultMongoTypeMapper(null))
        return mappingMongoConverter
    }

    @Bean
    fun transactionManager(mongoTemplate: MongoTemplate) = MongoTransactionManager(mongoTemplate.mongoDatabaseFactory)
}
