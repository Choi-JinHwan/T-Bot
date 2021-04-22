package dev.light.bot.infra.twitch4j

import com.github.twitch4j.chat.TwitchChat
import com.github.twitch4j.chat.events.channel.IRCMessageEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import javax.annotation.PreDestroy

@Component
class TwitchChatEventPublisher(
    val twitchChatClient: TwitchChat,
    val eventPublisher: ApplicationEventPublisher
) {

    private val joinedChannels
        get() = twitchChatClient.channels

    init {
        joinChannels("sah_yang")

        twitchChatClient.eventManager.onEvent(IRCMessageEvent::class.java) { rawEvent ->
            rawEvent.toApplicationEvent()?.run {
                eventPublisher.publishEvent(this)
            }
        }

    }

    @PreDestroy
    fun leaveAllChannels() {
        println("leave all")
        leaveChannels(*joinedChannels.toTypedArray())
    }

    private final fun joinChannels(vararg channelNames: String) {
        channelNames
            .filterNot(twitchChatClient::isChannelJoined)
            .forEach(twitchChatClient::joinChannel)
    }

    private final fun leaveChannels(vararg channelNames: String) {
        channelNames
            .filter(twitchChatClient::isChannelJoined)
            .forEach(twitchChatClient::leaveChannel)
    }

}

