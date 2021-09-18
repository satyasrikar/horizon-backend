package com.delegation.horizon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class HorizonDelegationServerApplication

fun main(args: Array<String>) {
    runApplication<HorizonDelegationServerApplication>(*args)
    println("[[App running!]]")
}
