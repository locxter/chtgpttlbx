package com.github.locxter.chtgpttlbx

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.formdev.flatlaf.FlatDarkLaf
import com.formdev.flatlaf.FlatLightLaf
import com.github.locxter.chtgpttlbx.gui.*
import com.github.locxter.chtgpttlbx.lib.OpenaiClient
import com.github.locxter.chtgpttlbx.lib.SettingsController
import com.github.locxter.chtgpttlbx.model.Chat
import com.github.locxter.chtgpttlbx.model.EChatLanguage
import com.github.locxter.chtgpttlbx.model.EColourTheme
import com.github.locxter.chtgpttlbx.model.ETool
import kotlinx.coroutines.runBlocking
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.io.File
import javax.swing.*
import javax.swing.border.EmptyBorder

@BetaOpenAI
fun main(args: Array<String>) {
    val settingsController = SettingsController(File("chtgpttlbx.properties"))
    var settings = settingsController.readSettings()
    val openaiClient = OpenaiClient(settings.openaiKey)
    var chat = Chat()
    var currentToolIndex = 0
    // Set a pleasing LaF
    try {
        UIManager.setLookAndFeel(
            when (settings.colourTheme) {
                EColourTheme.COLOUR_THEME_DARK -> FlatDarkLaf()
                EColourTheme.COLOUR_THEME_LIGHT -> FlatLightLaf()
            }
        )
    } catch (exception: Exception) {
        println("Failed to initialize LaF.")
    }
    // UI components
    val frame = JFrame("chtgpttlbx")
    val panel = JPanel()
    val constraints = GridBagConstraints()
    val toolSelector = JComboBox(ETool.values().map { it.displayName }.toTypedArray())
    val spacer1 = Spacer()
    val spacer2 = Spacer()
    val spacer3 = Spacer()
    val settingsButton = JButton("Settings")
    val tools = ETool.values().map { it.component.settings = settings; it.component }
    val toolView = ToolView(tools)
    val restartChatButton = JButton("(Re-)Start chat")
    val chatView = ChatView()
    val messageInput = HeightLimitedTextArea(48)
    val sendButton = JButton("Send")
    val aboutLabel = JLabel("2023 locxter")
    // Add functions to the buttons
    toolSelector.addActionListener {
        currentToolIndex = toolSelector.selectedIndex
        toolView.showTool(currentToolIndex)
    }
    settingsButton.addActionListener {
        val settingsDialog = SettingsDialog(frame, settings)
        val response = settingsDialog.run()
        if (response == JOptionPane.OK_OPTION) {
            settings = settingsDialog.settings
            openaiClient.openaiKey = settings.openaiKey
            for (tool in tools) {
                tool.settings = settings
            }
            settingsController.writeSettings(settings)
        }
    }
    restartChatButton.addActionListener {
        chat = tools[currentToolIndex].getInitialMessages()
        chat.messages.add(ChatMessage(
            ChatRole.Assistant,
            when (settings.chatLanguage) {
                EChatLanguage.CHAT_LANGUAGE_ENGLISH -> "I'm processing your initial prompt, this might take a while..."
                EChatLanguage.CHAT_LANGUAGE_GERMAN -> "Ich bearbeite deine Anfrage, das kann eine Weile dauern..."
            }
        ))
        chatView.chat = chat
        chat.messages.removeLast()
        openaiClient.chat = chat
        SwingUtilities.invokeLater {
            runBlocking {
                println(openaiClient.getAssistantResponse())
            }
            chat = openaiClient.chat
            chatView.chat = chat
        }
    }
    sendButton.addActionListener {
        if (messageInput.text.isNotBlank() && chat.messages.last().role == ChatRole.Assistant) {
            chat.messages.add(ChatMessage(ChatRole.User, messageInput.text))
            messageInput.text = ""
            chat.messages.add(ChatMessage(
                ChatRole.Assistant,
                when (settings.chatLanguage) {
                    EChatLanguage.CHAT_LANGUAGE_ENGLISH -> "I'm processing your subsequent prompt, this might take a while..."
                    EChatLanguage.CHAT_LANGUAGE_GERMAN -> "Ich bearbeite deine Anfrage, das kann eine Weile dauern..."
                }
            ))
            chatView.chat = chat
            chat.messages.removeLast()
            openaiClient.chat = chat
            SwingUtilities.invokeLater {
                runBlocking {
                    println(openaiClient.getAssistantResponse() + "\n")
                }
                chat = openaiClient.chat
                chatView.chat = chat
            }
        }
    }
    // Create the main panel
    panel.border = EmptyBorder(5, 5, 5, 5)
    panel.layout = GridBagLayout()
    constraints.insets = Insets(5, 5, 5, 5)
    constraints.fill = GridBagConstraints.HORIZONTAL
    constraints.weightx = 1.0
    constraints.gridx = 0
    constraints.gridy = 0
    panel.add(toolSelector, constraints)
    constraints.gridx = 1
    constraints.gridy = 0
    panel.add(spacer1, constraints)
    constraints.gridx = 2
    constraints.gridy = 0
    panel.add(spacer2, constraints)
    constraints.gridx = 3
    constraints.gridy = 0
    panel.add(spacer3, constraints)
    constraints.gridx = 4
    constraints.gridy = 0
    panel.add(settingsButton, constraints)
    constraints.gridx = 0
    constraints.gridy = 1
    constraints.gridwidth = 5
    panel.add(toolView, constraints)
    constraints.gridx = 0
    constraints.gridy = 2
    panel.add(restartChatButton, constraints)
    constraints.fill = GridBagConstraints.BOTH
    constraints.weighty = 1.0
    constraints.gridx = 0
    constraints.gridy = 3
    panel.add(chatView, constraints)
    constraints.fill = GridBagConstraints.HORIZONTAL
    constraints.weighty = 0.0
    constraints.gridx = 0
    constraints.gridy = 4
    constraints.gridwidth = 4
    panel.add(messageInput, constraints)
    constraints.fill = GridBagConstraints.BOTH
    constraints.gridx = 4
    constraints.gridy = 4
    constraints.gridwidth = 1
    panel.add(sendButton, constraints)
    constraints.fill = GridBagConstraints.RELATIVE
    constraints.gridx = 0
    constraints.gridy = 6
    constraints.gridwidth = 5
    panel.add(aboutLabel, constraints)
    // Create the main window
    frame.size = Dimension(640, 640)
    frame.minimumSize = Dimension(640, 640)
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.add(panel)
    frame.isVisible = true
}
