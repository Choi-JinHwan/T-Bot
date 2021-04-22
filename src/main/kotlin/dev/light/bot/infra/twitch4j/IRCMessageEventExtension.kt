package dev.light.bot.infra.twitch4j

import com.github.twitch4j.chat.events.channel.IRCMessageEvent
import dev.light.bot.domain.OtherTwitchBot
import dev.light.bot.domain.TwitchUserChatEvent
import org.springframework.context.ApplicationEvent

fun IRCMessageEvent.toApplicationEvent(): ApplicationEvent {
    if (isUserChat()) {
        return toUserChatEvent()
    }

    return SimpleIRCMessageApplicationEvent(this)
}

fun IRCMessageEvent.isUserChat() =
    commandType.equals("PRIVMSG") && !OtherTwitchBot.isBotUserId(userId)

fun IRCMessageEvent.toUserChatEvent(): TwitchUserChatEvent = TwitchUserChatEvent(
    channelId = channelId,
    channelName = channelName.orElse(""),
    userId = channelId,
    userName = userName,
    message = message.orElse("")
)