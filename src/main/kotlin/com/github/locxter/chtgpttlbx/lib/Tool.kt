package com.github.locxter.chtgpttlbx.lib

import com.aallam.openai.api.BetaOpenAI
import com.github.locxter.chtgpttlbx.gui.UneditableTextArea
import com.github.locxter.chtgpttlbx.model.Chat
import com.github.locxter.chtgpttlbx.model.Settings
import java.awt.GridBagConstraints
import javax.swing.JPanel

@OptIn(BetaOpenAI::class)
abstract class Tool() : JPanel() {
    protected val constraints = GridBagConstraints()
    protected val description = UneditableTextArea()
    var settings = Settings()

    constructor(settings: Settings) : this() {
        this.settings = settings
    }

    abstract fun getInitialMessages(): Chat
}
