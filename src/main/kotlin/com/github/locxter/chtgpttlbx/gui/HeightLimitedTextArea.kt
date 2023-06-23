package com.github.locxter.chtgpttlbx.gui

import java.awt.Dimension
import javax.swing.JScrollPane
import javax.swing.JTextArea
import kotlin.math.max
import kotlin.math.roundToInt

class HeightLimitedTextArea() : JScrollPane() {
    private val textArea = JTextArea()
    var visibleLines: Int = 1
        set(value) {
            field = max(value, 1)
            validate()
        }
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
        textArea.lineWrap = true
        textArea.wrapStyleWord = true
        setViewportView(textArea)
        validate()
    }

    constructor(visibleLines: Int) : this() {
        this.visibleLines = visibleLines
    }

    override fun validate() {
        super.validate()
        minimumSize = Dimension(0, ((visibleLines + .5) * this.getFontMetrics(this.font).height).roundToInt())
        preferredSize = Dimension(0, ((visibleLines + .5) * this.getFontMetrics(this.font).height).roundToInt())
    }
}
