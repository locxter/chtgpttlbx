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
class PresentationCreator : Tool() {
    private val titleLabel = JLabel("Presentation title:")
    private val titleInput = JTextField()
    private val authorLabel = JLabel("Author:")
    private val authorInput = JTextField()
    private val topicsLabel = JLabel("Topics:")
    private val topicsInput = JTextField()

    init {
        description.text =
            "This is a presentation creator in Markdown syntax. Remember to separate topics with a comma."
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
        add(titleLabel, constraints)
        constraints.fill = GridBagConstraints.HORIZONTAL
        constraints.gridx = 1
        constraints.gridy = 1
        add(titleInput, constraints)
        constraints.fill = GridBagConstraints.RELATIVE
        constraints.gridx = 0
        constraints.gridy = 2
        add(authorLabel, constraints)
        constraints.fill = GridBagConstraints.HORIZONTAL
        constraints.gridx = 1
        constraints.gridy = 2
        add(authorInput, constraints)
        constraints.fill = GridBagConstraints.RELATIVE
        constraints.gridx = 0
        constraints.gridy = 3
        add(topicsLabel, constraints)
        constraints.fill = GridBagConstraints.HORIZONTAL
        constraints.gridx = 1
        constraints.gridy = 3
        add(topicsInput, constraints)
    }

    override fun getInitialMessages(): Chat {
        return Chat(
            messages = mutableListOf(
                ChatMessage(
                    ChatRole.System,
                    when (settings.chatLanguage) {
                        EChatLanguage.CHAT_LANGUAGE_ENGLISH -> {
                            "You are an AI system for creating presentations with Markdown syntax, where you exclusively use bullet points and slide changes are indicated by `---`. These presentations are meant to be used in a professional or academic environment and have a correspondingly high standard. The user tells you the title, name of author and topics of the presentation. Insert these into the following template and adapt it if necessary with more pages. Template:\n" +
                                    "\n" +
                                    "```\n" +
                                    "# Titel\n" +
                                    "\n" +
                                    "<br><br><br><br><br><br><br><br>\n" +
                                    "\n" +
                                    "#### Author\n" +
                                    "\n" +
                                    "---\n" +
                                    "\n" +
                                    "# Agenda\n" +
                                    "\n" +
                                    "- Topic 1\n" +
                                    "- Topic 2\n" +
                                    "- Topic 3\n" +
                                    "\n" +
                                    "---\n" +
                                    "\n" +
                                    "# Topic 1\n" +
                                    "\n" +
                                    "- Point\n" +
                                    "- Point\n" +
                                    "- Point\n" +
                                    "\n" +
                                    "<br><br>\n" +
                                    "\n" +
                                    "**&rarr; Conclusion of the topic**\n" +
                                    "\n" +
                                    "---\n" +
                                    "\n" +
                                    "# Topic 2\n" +
                                    "\n" +
                                    "- Point\n" +
                                    "- Point\n" +
                                    "- Point\n" +
                                    "\n" +
                                    "<br><br>\n" +
                                    "\n" +
                                    "**&rarr; Conclusion of the topic**\n" +
                                    "\n" +
                                    "---\n" +
                                    "\n" +
                                    "# Thanks for your attention!\n" +
                                    "\n" +
                                    "Sources:\n" +
                                    "\n" +
                                    "- Source\n" +
                                    "- Source\n" +
                                    "- Source\n" +
                                    "```"
                        }

                        EChatLanguage.CHAT_LANGUAGE_GERMAN -> {
                            "Du bist ein KI-System zur Erstellung von Präsentationen im Markdown-Format, wobei du ausschließlich Stichpunkte verwendest und Seitenumbrüche durch `---` markiert werden. Die Präsentationen sollen im beruflichen bzw. akademischen Umfeld verwendet werden und einen dementsprechend gehobenen Anspruch haben. Der Nutzer gibt dir den Titel, Namen des Autoren sowie Themen der Präsentation. Setze diese in folgende Vorlage ein bzw. ergänze sie ggf. um weitere Seiten. Vorlage:\n" +
                                    "\n" +
                                    "```\n" +
                                    "# Titel\n" +
                                    "\n" +
                                    "<br><br><br><br><br><br><br><br>\n" +
                                    "\n" +
                                    "#### Autor\n" +
                                    "\n" +
                                    "---\n" +
                                    "\n" +
                                    "# Agenda\n" +
                                    "\n" +
                                    "- Thema 1\n" +
                                    "- Thema 2\n" +
                                    "- Thema 3\n" +
                                    "\n" +
                                    "---\n" +
                                    "\n" +
                                    "# Thema 1\n" +
                                    "\n" +
                                    "- Punkt\n" +
                                    "- Punkt\n" +
                                    "- Punkt\n" +
                                    "\n" +
                                    "<br><br>\n" +
                                    "\n" +
                                    "**&rarr; Zusammenfassung des Themas**\n" +
                                    "\n" +
                                    "---\n" +
                                    "\n" +
                                    "# Thema 2\n" +
                                    "\n" +
                                    "- Punkt\n" +
                                    "- Punkt\n" +
                                    "- Punkt\n" +
                                    "\n" +
                                    "<br><br>\n" +
                                    "\n" +
                                    "**&rarr; Zusammenfassung des Themas**\n" +
                                    "\n" +
                                    "---\n" +
                                    "\n" +
                                    "# Danke für Ihre Aufmerksamkeit!\n" +
                                    "\n" +
                                    "Quellen:\n" +
                                    "\n" +
                                    "- Quelle\n" +
                                    "- Quelle\n" +
                                    "- Quelle\n" +
                                    "```"
                        }
                    }
                ),
                ChatMessage(
                    ChatRole.User,
                    when (settings.chatLanguage) {
                        EChatLanguage.CHAT_LANGUAGE_ENGLISH -> "Create a presentation with the title \"${titleInput.text}\" and author \"${authorInput.text}\". Focus on the following topics: ${topicsInput.text}\n"
                        EChatLanguage.CHAT_LANGUAGE_GERMAN -> "Erstelle eine Präsentation mit dem Titel \"${titleInput.text}\", wobei du \"${authorInput.text}\" als Autoren angibst. Gehe besonders auf folgende Themen ein: ${topicsInput.text}\n"
                    }
                ),
            )
        )
    }
}
