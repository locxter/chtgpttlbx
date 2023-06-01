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
class OverviewCreator : Tool() {
    private val topicLabel = JLabel("Overview topic:")
    private val topicInput = JTextField()
    private val keyAspectsLabel = JLabel("Key aspects:")
    private val keyAspectsInput = JTextField()

    init {
        description.text =
            "This is a topic overview creator in Markdown syntax. Remember to separate key aspects with a comma."
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
        add(topicLabel, constraints)
        constraints.fill = GridBagConstraints.HORIZONTAL
        constraints.gridx = 1
        constraints.gridy = 1
        add(topicInput, constraints)
        constraints.fill = GridBagConstraints.RELATIVE
        constraints.gridx = 0
        constraints.gridy = 2
        add(keyAspectsLabel, constraints)
        constraints.fill = GridBagConstraints.HORIZONTAL
        constraints.gridx = 1
        constraints.gridy = 2
        add(keyAspectsInput, constraints)

    }

    override fun getInitialMessages(): Chat {
        return Chat(
            messages = mutableListOf(
                ChatMessage(
                    ChatRole.System,
                    when (settings.chatLanguage) {
                        EChatLanguage.CHAT_LANGUAGE_ENGLISH -> {
                            "You are an AI system for creating overviews with Markdown syntax, where you exclusively use bullet points and slide changes are indicated by `---`. These overviews are meant to be used in a professional or academic environment and have a correspondingly high standard. The user tells you the topic of the overview and possibly key aspects. Insert these into the following template and adapt it if necessary with more points. Template:\n" +
                                    "\n" +
                                    "```\n" +
                                    "# Title\n" +
                                    "\n" +
                                    "- Point\n" +
                                    "- Point\n" +
                                    "- Point\n" +
                                    "\n" +
                                    "=> Conclusion\n" +
                                    "```"
                        }

                        EChatLanguage.CHAT_LANGUAGE_GERMAN -> {
                            "Du bist ein KI-System zur Erstellung von Themenüberblicken im Markdown-Format, wobei du ausschließlich Stichpunkte verwendest und Seitenumbrüche durch `---` markiert werden. Die Überblicke sollen im beruflichen bzw. akademischen Umfeld verwendet werden und einen dementsprechend gehobenen Anspruch haben. Der Nutzer gibt dir das Thema des Überblicks sowie ggf. thematische Schwerpunkte. Setze diese in folgende Vorlage ein bzw. ergänze sie ggf. um weitere Punkte. Vorlage:\n" +
                                    "\n" +
                                    "```\n" +
                                    "# Thema\n" +
                                    "\n" +
                                    "- Punkt\n" +
                                    "- Punkt\n" +
                                    "- Punkt\n" +
                                    "\n" +
                                    "=> Zusammenfassung\n" +
                                    "```"
                        }
                    }
                ),
                ChatMessage(
                    ChatRole.User,
                    when (settings.chatLanguage) {
                        EChatLanguage.CHAT_LANGUAGE_ENGLISH -> "Create an overview of the topic \"${topicInput.text}\". Focus on the following aspects: ${keyAspectsInput.text}\n"
                        EChatLanguage.CHAT_LANGUAGE_GERMAN -> "Erstelle einen Überblick über das Thema \"${topicInput.text}\". Gehe besonders auf folgende Schwerpunkte ein: ${keyAspectsInput.text}\n"
                    }
                ),
            )
        )
    }
}
