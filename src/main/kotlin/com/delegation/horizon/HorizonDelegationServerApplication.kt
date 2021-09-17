package com.delegation.horizon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HorizonDelegationServerApplication

fun main(args: Array<String>) {
    runApplication<HorizonDelegationServerApplication>(*args)
    println("[[App running!]]")
}
