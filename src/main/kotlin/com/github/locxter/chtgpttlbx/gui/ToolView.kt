package com.github.locxter.chtgpttlbx.gui

import com.github.locxter.chtgpttlbx.lib.Tool
import java.awt.CardLayout
import javax.swing.JPanel
import kotlin.math.max
import kotlin.math.min

class ToolView() : JPanel() {
    private val cardLayout = CardLayout()
    private var currentToolIndex = 0
    var tools: List<Tool> = listOf()
        set(value) {
            removeAll()
            for (i in value.indices) {
                add(i.toString(), value[i])
            }
            field = value
            showTool(0)
        }

    init {
        layout = cardLayout
    }

    constructor(tools: List<Tool>) : this() {
        this.tools = tools
    }

    override fun validate() {
        super.validate()
        minimumSize = tools[currentToolIndex].minimumSize
        preferredSize = tools[currentToolIndex].preferredSize
    }

    fun showTool(index: Int) {
        if (tools.isNotEmpty()) {
            currentToolIndex = min(max(index, 0), tools.lastIndex)
            cardLayout.show(this, currentToolIndex.toString())
            validate()
        }
    }
}
