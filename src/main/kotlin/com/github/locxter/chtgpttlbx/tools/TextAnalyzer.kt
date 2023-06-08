package com.github.locxter.chtgpttlbx.tools

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.github.locxter.chtgpttlbx.gui.HeightLimitedTextArea
import com.github.locxter.chtgpttlbx.lib.Tool
import com.github.locxter.chtgpttlbx.model.Chat
import com.github.locxter.chtgpttlbx.model.EChatLanguage
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.border.EmptyBorder
import kotlin.math.min

@BetaOpenAI
class TextAnalyzer : Tool() {
    private val titleLabel = JLabel("Analysis title:")
    private val titleInput = JTextField()
    private val keyAspectsLabel = JLabel("Key aspects:")
    private val keyAspectsInput = JTextField()
    private val originalTextLabel = JLabel("Original text:")
    private val originalTextInput = HeightLimitedTextArea(3)

    init {
        description.text =
            "This is a text analyzer in Markdown syntax. Remember to separate key aspects with a comma."
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
        add(keyAspectsLabel, constraints)
        constraints.fill = GridBagConstraints.HORIZONTAL
        constraints.gridx = 1
        constraints.gridy = 2
        add(keyAspectsInput, constraints)
        constraints.fill = GridBagConstraints.RELATIVE
        constraints.gridx = 0
        constraints.gridy = 3
        add(originalTextLabel, constraints)
        constraints.fill = GridBagConstraints.HORIZONTAL
        constraints.gridx = 1
        constraints.gridy = 3
        add(originalTextInput, constraints)
    }

    override fun getInitialMessages(): Chat {
        return Chat(
            messages = mutableListOf(
                ChatMessage(
                    ChatRole.System,
                    when (settings.chatLanguage) {
                        EChatLanguage.CHAT_LANGUAGE_ENGLISH -> {
                            "You are an AI system for creating text analysis with Markdown syntax, where you exclusively use full sentences. These analysis are meant to be used in a professional or academic environment and have a correspondingly high standard. The user tells you the title of the text, the text itself and possibly key aspects. Structure the text into an introduction, main part and conclusion, with the introduction containing formalities such as text type, title, author, year of publication, source, topic and addressee, the main part analyzing the text in depth according to the PEE-principle (point, evidence, explanation) and the conclusion summarizing the central results of the analysis. Insert these into the following template and adapt it if necessary with more points. Template:\n" +
                                    "\n" +
                                    "```\n" +
                                    "# Title\n" +
                                    "\n" +
                                    "Introduction\n" +
                                    "\n" +
                                    "Main part\n" +
                                    "\n" +
                                    "Conclusion\n" +
                                    "```"
                        }

                        EChatLanguage.CHAT_LANGUAGE_GERMAN -> {
                            "Du bist ein KI-System zur Erstellung von Textanalysen im Markdown-Format, wobei du ausschließlich vollständige Sätze verwendest. Die Analysen sollen im beruflichen bzw. akademischen Umfeld verwendet werden und einen dementsprechend gehobenen Anspruch haben. Der Nutzer gibt dir den Titel des Textes, den Text selbst sowie ggf. thematische Schwerpunkte der Analyse. Teile den Text in Einleitung, Hauptteil und Schluss, wobei die Einleitung Formalia wie Textart, Titel, Autor, Erscheinungsjahr, Quelle, Thema und Addressat beinhaltet, der Hauptteil den Text auf die Schwerpunkte eingehend nach dem Drei-B-Prinzip (Behauptung, Begründung, Beleg) analysiert und im Schluss die zentralen Analyseergebnisse zusammengefasst werden. Setze diese in folgende Vorlage ein bzw. ergänze sie ggf. um weitere Punkte. Vorlage:\n" +
                                    "\n" +
                                    "```\n" +
                                    "# Titel\n" +
                                    "\n" +
                                    "Einleitung\n" +
                                    "\n" +
                                    "Hauptteil\n" +
                                    "\n" +
                                    "Schluss\n" +
                                    "```"
                        }
                    }
                ),
                ChatMessage(
                    ChatRole.User,
                    when (settings.chatLanguage) {
                        EChatLanguage.CHAT_LANGUAGE_ENGLISH -> {
                            "Create an analysis of the following text with the title \"${titleInput.text}\". Focus on the following aspects: ${keyAspectsInput.text}. Original text:\n" +
                                    "\n" +
                                    "```\n" +
                                    "${originalTextInput.text.substring(0, min(originalTextInput.text.length, 12500))}\n" +
                                    "```"
                        }
                        EChatLanguage.CHAT_LANGUAGE_GERMAN -> {
                            "Erstelle eine Analyse des nachfolgenden Textes mit dem Titel \"${titleInput.text}\". Gehe besonders auf folgende Schwerpunkte ein: ${keyAspectsInput.text}. Originaler Text:\n" +
                                    "\n" +
                                    "```\n" +
                                    "${originalTextInput.text.substring(0, min(originalTextInput.text.length, 12500))}\n" +
                                    "```"
                        }
                    }
                ),
            )
        )
    }
}
