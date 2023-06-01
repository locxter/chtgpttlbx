package com.github.locxter.chtgpttlbx.gui

import java.awt.Dimension
import javax.swing.JScrollPane
import javax.swing.JTextArea
import kotlin.math.max

class HeightLimitedTextArea(maxHeight: Int) : JScrollPane() {
    private val textArea = JTextArea()
    var text: String = ""
        get() {
            return textArea.text
        }
        set(value) {
            textArea.text = value
            field = value
        }

    init {
        // Configure the scroll pane
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER)
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED)
        minimumSize = Dimension(0, max(maxHeight, 0))
        preferredSize = Dimension(0, max(maxHeight, 0))
        textArea.lineWrap = true
        textArea.wrapStyleWord = true
        setViewportView(textArea)
    }
}
