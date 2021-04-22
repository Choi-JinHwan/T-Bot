package dev.light.bot

import com.github.twitch4j.chat.TwitchChat
import com.github.twitch4j.chat.events.channel.IRCMessageEvent
import dev.light.bot.event.TwitchUserChatEvent
import org.springframework.context.ApplicationEvent
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

        twitchChatClient.eventManager.onEvent(IRCMessageEvent::class.java) { event ->
            eventPublisher.publishEvent(event.toApplicationEvent())
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

fun IRCMessageEvent.toApplicationEvent(): ApplicationEvent {
    if (isUserChat()) {
        return toUserChatEvent()
    }

    return SimpleIRCMessageApplicationEvent(this)
}

data class SimpleIRCMessageApplicationEvent(val event: IRCMessageEvent) : ApplicationEvent(event)

fun IRCMessageEvent.isUserChat() =
    commandType.equals("PRIVMSG") && !OtherTwitchBot.isBotUserId(userId)

fun IRCMessageEvent.toUserChatEvent(): TwitchUserChatEvent = TwitchUserChatEvent(
    channelId = channelId,
    channelName = channelName.orElse(""),
    userId = channelId,
    userName = userName,
    message = message.orElse("")
)