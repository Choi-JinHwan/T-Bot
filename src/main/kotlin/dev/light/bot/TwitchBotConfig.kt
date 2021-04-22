package dev.light.bot

import com.github.philippheuer.credentialmanager.CredentialManager
import com.github.philippheuer.credentialmanager.CredentialManagerBuilder
import com.github.twitch4j.TwitchClient
import com.github.twitch4j.TwitchClientBuilder
import com.github.twitch4j.auth.providers.TwitchIdentityProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TwitchBotConfig {

    @Bean
    fun twitchClient(credentialManager: CredentialManager): TwitchClient = TwitchClientBuilder.builder()
        .withEnableChat(true)
        .withCredentialManager(credentialManager)
        .build()

    @Bean
    fun twitchChatClient(twitchClient: TwitchClient) = twitchClient.chat

    @Bean
    fun credentialManager(twitchProperty: TwitchProperty) = CredentialManagerBuilder.builder().build().also {
        it.registerIdentityProvider(
            TwitchIdentityProvider(
                twitchProperty.clientId,
                twitchProperty.clientSecret,
                twitchProperty.redirectUrl
            )
        )
    }

}