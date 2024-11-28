package com.example.demo.repository.elastic

import com.example.demo.entity.elastic.TextMessageElastic
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface TextMessageElasticRepository : ElasticsearchRepository<TextMessageElastic, String>
