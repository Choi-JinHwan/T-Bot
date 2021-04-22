package dev.light.bot.event

import org.springframework.context.ApplicationEvent

data class TwitchUserChatEvent(
    val channelId: String,
    val channelName: String,
    val userId: String,
    val userName: String,
    val message: String
) : ApplicationEvent("none")