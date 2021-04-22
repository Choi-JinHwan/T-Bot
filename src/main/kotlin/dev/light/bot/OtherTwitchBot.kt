package dev.light.bot

enum class OtherTwitchBot(val userId: String) {
    NIGHT_BOT("83257026"),
    SSAKDOOK("83257026");

    companion object {
        fun isBotUserId(userId: String): Boolean = values().map { it.userId }.contains(userId)
    }

}