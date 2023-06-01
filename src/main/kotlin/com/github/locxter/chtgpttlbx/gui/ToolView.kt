package com.github.locxter.chtgpttlbx.gui

import com.github.locxter.chtgpttlbx.lib.Tool
import java.awt.CardLayout
import javax.swing.JPanel
import kotlin.math.max
import kotlin.math.min

class ToolView() : JPanel() {
    private val cardLayout = CardLayout()
    var tools: List<Tool> = listOf()
        set(value) {
            removeAll()
            for (i in value.indices) {
                add(i.toString(), value[i])
            }
            field = value
            if (field.isNotEmpty()) {
                showTool(0)
            }
        }

    init {
        layout = cardLayout
    }

    constructor(tools: List<Tool>) : this() {
        this.tools = tools
    }

    fun showTool(index: Int) {
        val validIndex = min(max(index, 0), tools.lastIndex)
        cardLayout.show(this, validIndex.toString())
        preferredSize = tools[validIndex].preferredSize
    }
}
