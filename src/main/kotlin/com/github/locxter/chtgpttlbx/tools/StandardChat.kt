package com.github.locxter.chtgpttlbx.tools

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.github.locxter.chtgpttlbx.lib.Tool
import com.github.locxter.chtgpttlbx.model.Chat
import com.github.locxter.chtgpttlbx.model.EChatLanguage
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.border.EmptyBorder

@BetaOpenAI
class StandardChat : Tool() {
    private val initialPromptLabel = JLabel("Initial prompt")
    private val initialPromptInput = JTextField()

    init {
        description.text = "This is the standard ChatGPT chat known from the website."
        // Create the panel
        border = EmptyBorder(5, 5, 5, 5)
        layout = GridBagLayout()
        constraints.insets = Insets(5, 5, 5, 5)
        constraints.fill = GridBagConstraints.RELATIVE
        constraints.weightx = 1.0
        constraints.gridx = 0
        constraints.gridy = 0
        constraints.gridwidth = 2
        add(description, constraints)
        constraints.gridx = 0
        constraints.gridy = 1
        constraints.gridwidth = 1
        add(initialPromptLabel, constraints)
        constraints.fill = GridBagConstraints.HORIZONTAL
        constraints.gridx = 1
        constraints.gridy = 1
        add(initialPromptInput, constraints)
    }

    override fun getInitialMessages(): Chat {
        return Chat(
            messages = mutableListOf(
                ChatMessage(
                    ChatRole.System,
                    when (settings.chatLanguage) {
                        EChatLanguage.CHAT_LANGUAGE_ENGLISH -> "You are an universal AI assistant."
                        EChatLanguage.CHAT_LANGUAGE_GERMAN -> "Du bist ein universeller KI-Assistent."
                    }
                ),
                ChatMessage(ChatRole.User, initialPromptInput.text)
            )
        )
    }
}
