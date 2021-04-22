package dev.light.bot

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "twitch")
data class TwitchProperty(
    val clientId: String,
    val clientSecret: String,
    val accessToken: String,
    val redirectUrl: String
)