package com.github.locxter.chtgpttlbx.tools

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.github.locxter.chtgpttlbx.lib.Tool
import com.github.locxter.chtgpttlbx.lib.TranscriptClient
import com.github.locxter.chtgpttlbx.model.Chat
import com.github.locxter.chtgpttlbx.model.EChatLanguage
import com.github.locxter.chtgpttlbx.model.EModel
import com.github.locxter.chtgpttlbx.model.VideoId
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.border.EmptyBorder
import kotlin.math.min

@BetaOpenAI
class VideoSummarizer : Tool() {
    private val titleLabel = JLabel("Summary title:")
    private val titleInput = JTextField()
    private val keyAspectsLabel = JLabel("Key aspects:")
    private val keyAspectsInput = JTextField()
    private val videoIdLabel = JLabel("Video ID:")
    private val videoIdInput = JTextField()

    init {
        description.text =
            "This is a YouTube video summarizer in Markdown syntax based on getsubs.cc. Remember that the video ID is the last part of the URL after \"watch?v=\" or \"youtu.be/\"."
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
        add(videoIdLabel, constraints)
        constraints.fill = GridBagConstraints.HORIZONTAL
        constraints.gridx = 1
        constraints.gridy = 3
        add(videoIdInput, constraints)
    }

    override fun getInitialMessages(): Chat {
        val videoId = videoIdInput.text.substringAfter("watch?v=").substringAfter("youtu.be/")
        val transcript = TranscriptClient.getTranscript(VideoId(videoId), settings.chatLanguage)
        return Chat(
            messages = mutableListOf(
                ChatMessage(
                    ChatRole.System,
                    when (settings.chatLanguage) {
                        EChatLanguage.CHAT_LANGUAGE_ENGLISH -> {
                            "You are an AI system for creating video summaries with Markdown syntax, where you exclusively use bullet points. These summaries are meant to be used in a professional or academic environment and have a correspondingly high standard. The user tells you the title of the video, an automatically generated transcript and possibly key aspects. Insert these into the following template and adapt it if necessary with more points. Describe the core statements of the video as directly as possible, ignore the linguistic distance to the summarized content and never use the subjunctive or indirect speech. Also remove any sponsorship or advertising segments. Template:\n" +
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
                            "Du bist ein KI-System zur Erstellung von Videozusammenfassungen im Markdown-Format, wobei du ausschließlich Stichpunkte verwendest. Die Zusammenfassungen sollen im beruflichen bzw. akademischen Umfeld verwendet werden und einen dementsprechend gehobenen Anspruch haben. Der Nutzer gibt dir den Titel des Videos, ein automatisch generiertes Transkript des Videos sowie ggf. thematische Schwerpunkte der Zusammenfassung. Setze diese in folgende Vorlage ein bzw. ergänze sie ggf. um weitere Punkte. Beschreibe die Kernaussagen des Videos möglichst direkt, ignoriere die sprachliche Distanz zum Zusammengefassten und verwende nie den Konjunktiv oder indirekte Rede. Entferne auch alle Sponsoren- bzw. Werbesegmente. Vorlage:\n" +
                                    "\n" +
                                    "```\n" +
                                    "# Titel\n" +
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
                        EChatLanguage.CHAT_LANGUAGE_ENGLISH -> {
                            "Create a summary of the following video transcript with the title \"${titleInput.text}\". Focus on the following aspects: ${keyAspectsInput.text}. Tanscript:\n" +
                                    "\n" +
                                    "```\n" +
                                    "${transcript.content.substring(0, min(transcript.content.length, settings.model.maxContext))}\n" +
                                    "```"
                        }

                        EChatLanguage.CHAT_LANGUAGE_GERMAN -> {
                            "Erstelle eine Zusammenfassung des nachfolgend Videotranskripts mit dem Titel \"${titleInput.text}\". Gehe besonders auf folgende Schwerpunkte ein: ${keyAspectsInput.text}. Transskript:\n" +
                                    "\n" +
                                    "```\n" +
                                    "${transcript.content.substring(0, min(transcript.content.length, settings.model.maxContext))}\n" +
                                    "```"
                        }
                    }
                ),
            )
        )
    }
}
