package com.github.locxter.chtgpttlbx.gui

import com.formdev.flatlaf.FlatLaf
import java.awt.Color
import javax.swing.JTextArea
import javax.swing.border.EmptyBorder

class UneditableTextArea() : JTextArea() {
    var useEditableStyle = false
        set(value) {
            if (value) {
                background = if (FlatLaf.isLafDark()) {
                    Color(70, 73, 75)
                } else {
                    Color(255, 255, 255)
                }
                border = EmptyBorder(5, 5, 5, 5)
            } else {
                background = if (FlatLaf.isLafDark()) {
                    Color(60, 63, 65)
                } else {
                    Color(242, 242, 242)
                }
                border = null
            }
            field = value
        }
    var wrapText = false
        set(value) {
            lineWrap = value
            wrapStyleWord = value
            field = value
        }

    init {
        isEditable = false
    }

    constructor(useEditableStyle: Boolean) : this() {
        this.useEditableStyle = useEditableStyle
    }


    constructor(text: String) : this() {
        this.text = text
    }

    constructor(useEditableStyle: Boolean, text: String) : this() {
        this.useEditableStyle = useEditableStyle
        this.text = text
    }

    constructor(useEditableStyle: Boolean, text: String, wrapText: Boolean) : this() {
        this.useEditableStyle = useEditableStyle
        this.text = text
        this.wrapText = wrapText
    }
}
