package com.delegation.horizon

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@OpenAPIDefinition(info = Info(
    title = "Horizon API",
    version = "1.1.6",
    description = "Information regarding Horizon Client"
)
)
class HorizonDelegationServerApplication

fun main(args: Array<String>) {
    runApplication<HorizonDelegationServerApplication>(*args)
    println("[[App running!]]")
}
