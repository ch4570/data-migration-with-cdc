package com.example.demo.repository.elastic

import com.example.demo.entity.elastic.SingleFileElastic
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface SingleFileElasticRepository : ElasticsearchRepository<SingleFileElastic, String>
