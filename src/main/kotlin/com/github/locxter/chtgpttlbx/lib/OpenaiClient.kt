package com.github.locxter.chtgpttlbx.lib

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.LoggingConfig
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import com.github.locxter.chtgpttlbx.model.Chat
import kotlin.time.Duration.Companion.seconds

@OptIn(BetaOpenAI::class)
class OpenaiClient() {
    var openaiKey: String = ""
        set(value) {
            openai = OpenAI(
                OpenAIConfig(
                    token = value,
                    timeout = Timeout(socket = 60.seconds),
                    logging = LoggingConfig(LogLevel.None)
                )
            )
            field = value
        }
    var modelId: ModelId = ModelId("gpt-3.5-turbo")
    private var openai: OpenAI? = null
    var chat: Chat = Chat()

    constructor(apiKey: String) : this() {
        this.openaiKey = apiKey
    }

    constructor(apiKey: String, model: ModelId) : this() {
        this.openaiKey = apiKey
        this.modelId = model
    }

    fun addSystemMessage(content: String) {
        chat.messages.add(ChatMessage(ChatRole.System, content))
    }

    fun addUserMessage(content: String) {
        chat.messages.add(ChatMessage(ChatRole.User, content))
    }

    suspend fun getAssistantResponse(): String? {
        val chatCompletionRequest = ChatCompletionRequest(
            model = modelId,
            messages = chat.messages
        )
        if (openai == null) {
            return null
        }
        val chatCompletion = openai!!.chatCompletion(chatCompletionRequest)
        if (chatCompletion.choices.isEmpty()) {
            return null
        }
        val message = chatCompletion.choices[0].message!!
        chat.messages.add(message)
        return message.content
    }

    fun resetChatHistory() {
        chat.messages.clear()
    }
}
