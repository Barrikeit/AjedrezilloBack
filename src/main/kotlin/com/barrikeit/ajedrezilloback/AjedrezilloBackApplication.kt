package com.barrikeit.ajedrezilloback

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
class AjedrezilloBackApplication

fun main(args: Array<String>) {
    runApplication<AjedrezilloBackApplication>(*args)
}