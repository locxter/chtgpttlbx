package com.github.locxter.chtgpttlbx.lib

import com.github.locxter.chtgpttlbx.model.EChatLanguage
import com.github.locxter.chtgpttlbx.model.EColourTheme
import com.github.locxter.chtgpttlbx.model.EModel
import com.github.locxter.chtgpttlbx.model.Settings
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*
import javax.swing.JOptionPane

class SettingsController() {
    private var file: File? = null

    constructor(file: File) : this() {
        this.file = file
    }

    fun readSettings(): Settings {
        return try {
            val properties = Properties()
            val fileInputStream = FileInputStream(file!!)
            properties.load(fileInputStream)
            val colourTheme = properties.getProperty("colourTheme", EColourTheme.COLOUR_THEME_DARK.name)
            val openaiKey = properties.getProperty("openaiKey", "")
            val chatLanguage = properties.getProperty("chatLanguage", EChatLanguage.CHAT_LANGUAGE_ENGLISH.name)
            val model = properties.getProperty("model", EModel.GPT_35_TURBO.name)
            Settings(
                EColourTheme.valueOf(colourTheme),
                openaiKey,
                EChatLanguage.valueOf(chatLanguage),
                EModel.valueOf(model)
            )
        } catch (exception: Exception) {
            // Display an error if something does not work as expected
            JOptionPane.showMessageDialog(
                null, "Failed to read settings, falling back to defaults", "Error",
                JOptionPane.ERROR_MESSAGE
            )
            println(exception)
            Settings()
        }
    }

    fun writeSettings(settings: Settings) {
        try {
            val properties = Properties()
            properties.setProperty("colourTheme", settings.colourTheme.name)
            properties.setProperty("openaiKey", settings.openaiKey)
            properties.setProperty("chatLanguage", settings.chatLanguage.name)
            properties.setProperty("model", settings.model.name)
            val fileOutputStream = FileOutputStream(file!!)
            properties.store(fileOutputStream, null)
        } catch (exception: Exception) {
            // Display an error if something does not work as expected
            JOptionPane.showMessageDialog(
                null, "Failed to write settings", "Error",
                JOptionPane.ERROR_MESSAGE
            )
            println(exception)
            Settings()
        }
    }
}
