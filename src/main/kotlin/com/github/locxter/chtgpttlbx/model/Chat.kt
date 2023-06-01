package com.github.locxter.chtgpttlbx.model

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatMessage

@BetaOpenAI
data class Chat(
    val messages: MutableList<ChatMessage> = mutableListOf()
)
