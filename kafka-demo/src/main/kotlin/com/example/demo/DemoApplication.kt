package com.example.demo

import com.example.demo.repository.elastic.SingleFileElasticRepository
import com.example.demo.repository.elastic.TextMessageElasticRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class DemoApplication(
	private val fileElasticRepository: SingleFileElasticRepository,
	private val textMessageElasticRepository: TextMessageElasticRepository,
) : CommandLineRunner {
	override fun run(vararg args: String?) {
		fileElasticRepository.findAll().forEach { println("fileResult = [$it]") }
		textMessageElasticRepository.findAll().forEach { println("textResult = [$it]") }
	}
}

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}
