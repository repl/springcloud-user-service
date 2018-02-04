package org.repl.springcloud

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableEurekaClient
@Configuration
class UserServiceApplication

fun main(args: Array<String>) {
    SpringApplication.run(UserServiceApplication::class.java, *args)
}