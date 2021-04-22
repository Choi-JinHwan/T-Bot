package dev.light.bot.presentation

import com.github.twitch4j.chat.TwitchChat
import dev.light.bot.domain.TwitchUserChatEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class TwitchUserChatListener(val twitchChat: TwitchChat) {

    @EventListener
    fun printListen(userUserChatEvent: TwitchUserChatEvent) {
        println("$userUserChatEvent")
    }

}