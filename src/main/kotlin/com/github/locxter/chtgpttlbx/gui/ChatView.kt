package com.github.locxter.chtgpttlbx.gui

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatRole
import com.github.locxter.chtgpttlbx.model.Chat
import java.awt.*
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.border.EmptyBorder

@OptIn(BetaOpenAI::class)
class ChatView : JScrollPane() {
    var chat: Chat = Chat()
        set(value) {
            messageTextAreas.clear()
            for (message in value.messages) {
                messageTextAreas.add(
                    UneditableTextArea(
                        true,
                        when (message.role) {
                            ChatRole.System -> "chtgpttlbx"
                            ChatRole.User -> "Me"
                            ChatRole.Assistant -> "ChatGPT"
                            else -> "chtgpttlbx"
                        } + ":\n" + message.content,
                        true
                    )
                )
            }
            field = value
            assemblePanel()
        }
    private val messageTextAreas: MutableList<UneditableTextArea> = mutableListOf()
    private val panel = JPanel()
    private val constraints = GridBagConstraints()
    private val spacer = Spacer()

    init {
        // Configure the scroll pane
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER)
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED)
        panel.border = EmptyBorder(5, 5, 5, 5)
        panel.layout = GridBagLayout()
        constraints.insets = Insets(5, 5, 5, 5)
        constraints.weightx = 1.0
        assemblePanel()
    }

    override fun validate() {
        super.validate()
        panel.preferredSize = Dimension(preferredSize.width, panel.preferredSize.height)
        if (isOverflowing() && panel.componentCount == messageTextAreas.size + 1) {
            panel.remove(messageTextAreas.size)
        } else if (!isOverflowing() && panel.componentCount == messageTextAreas.size) {
            constraints.fill = GridBagConstraints.BOTH
            constraints.weighty = 1.0
            constraints.gridx = 0
            constraints.gridy = messageTextAreas.size
            panel.add(spacer, constraints)
        }
    }

    private fun assemblePanel() {
        panel.removeAll()
        for (i in 0 until messageTextAreas.size) {
            messageTextAreas[i].lineWrap = true
            messageTextAreas[i].wrapStyleWord = true
            constraints.fill = GridBagConstraints.HORIZONTAL
            constraints.weighty = 0.0
            constraints.gridx = 0
            constraints.gridy = i
            panel.add(messageTextAreas[i], constraints)
        }
        setViewportView(panel)
        validate()
    }

    private fun isOverflowing(): Boolean {
        if (viewport.view == null) return false
        return viewport.view.preferredSize.height > viewport.size.height
    }
}
