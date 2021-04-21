package dev.light.bot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TwitchBotApplication

fun main(args: Array<String>) {
    runApplication<TwitchBotApplication>(*args)
}
