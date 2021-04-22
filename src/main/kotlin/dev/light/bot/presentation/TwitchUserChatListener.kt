package dev.light.bot.presentation

import com.github.twitch4j.chat.TwitchChat
import dev.light.bot.domain.TwitchUserChatEvent
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class TwitchUserChatListener(val twitchChat: TwitchChat) {

    companion object {
        private val logger = LoggerFactory.getLogger(this.javaClass)
    }

    @EventListener
    fun loggingListener(userChatEvent: TwitchUserChatEvent) {
        if (logger.isTraceEnabled) {
            logger.trace("{}", userChatEvent)
        }
    }

}