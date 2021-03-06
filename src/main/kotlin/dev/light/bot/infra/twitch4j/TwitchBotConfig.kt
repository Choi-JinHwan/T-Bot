package dev.light.bot.infra.twitch4j

import com.github.philippheuer.credentialmanager.CredentialManager
import com.github.philippheuer.credentialmanager.CredentialManagerBuilder
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential
import com.github.twitch4j.TwitchClient
import com.github.twitch4j.TwitchClientBuilder
import com.github.twitch4j.chat.TwitchChat
import dev.light.bot.infra.TwitchProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TwitchBotConfig {

    @Bean
    fun defaultCredential(twitchProperty: TwitchProperty): OAuth2Credential =
        OAuth2Credential("twitch", twitchProperty.accessToken)

    @Bean
    fun twitchClient(
        credentialManager: CredentialManager,
        defaultCredential: OAuth2Credential
    ): TwitchClient =
        TwitchClientBuilder.builder()
            .withCredentialManager(credentialManager)
            .withEnableChat(true)
            .withChatAccount(defaultCredential)
            .build()

    @Bean
    fun twitchChatClient(twitchClient: TwitchClient): TwitchChat = twitchClient.chat

    @Bean
    fun credentialManager(twitchProperty: TwitchProperty): CredentialManager =
        CredentialManagerBuilder.builder().build()

}