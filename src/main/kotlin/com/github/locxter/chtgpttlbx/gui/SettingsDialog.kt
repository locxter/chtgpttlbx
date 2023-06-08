package com.github.locxter.chtgpttlbx.gui

import com.github.locxter.chtgpttlbx.lib.SettingsController
import com.github.locxter.chtgpttlbx.model.EChatLanguage
import com.github.locxter.chtgpttlbx.model.EColourTheme
import com.github.locxter.chtgpttlbx.model.Settings
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.io.File
import javax.swing.*
import javax.swing.border.EmptyBorder
import javax.swing.filechooser.FileNameExtensionFilter

class SettingsDialog(parent: JFrame) : JDialog(parent, "Settings", true) {
    private val panel = JPanel()
    private val constraints = GridBagConstraints()
    private val colourThemeLabel = JLabel("Colour theme:")
    private val colourThemeInput = JComboBox(EColourTheme.values().map { it.displayName }.toTypedArray())
    private val openaiKeyLabel = JLabel("OpenAI key:")
    private val openaiKeyInput = JTextField()
    private val chatLanguageLabel = JLabel("Chat language:")
    private val chatLanguageInput = JComboBox(EChatLanguage.values().map { it.displayName }.toTypedArray())
    private val restartMessage = JLabel("Restart the application for the all changes to take effect.")
    private val versionLabel = JLabel("v1.0")
    private val backupButton = JButton("Backup")
    private val restoreButton = JButton("Restore")
    private val spacer = Spacer()
    private val okButton = JButton("OK")
    private val cancelButton = JButton("Cancel")
    private var status: Int = JOptionPane.OK_OPTION
    var settings = Settings()
        private set(value) {
            colourThemeInput.selectedIndex = value.colourTheme.ordinal
            openaiKeyInput.text = value.openaiKey
            chatLanguageInput.selectedIndex = value.chatLanguage.ordinal
            field = value
        }

    init {
        // Add functions to the buttons
        okButton.addActionListener {
            dispose()
            status = JOptionPane.OK_OPTION
        }
        cancelButton.addActionListener {
            dispose()
            status = JOptionPane.CANCEL_OPTION
        }
        backupButton.addActionListener {
            val filter = FileNameExtensionFilter("Settings", "properties")
            val fileChooser = JFileChooser()
            fileChooser.fileFilter = filter
            val option = fileChooser.showSaveDialog(SwingUtilities.getWindowAncestor(this) as JFrame)
            if (option == JFileChooser.APPROVE_OPTION) {
                val settingsController = SettingsController(fileChooser.selectedFile)
                settingsController.writeSettings(settings)
            }
        }
        restoreButton.addActionListener {
            val filter = FileNameExtensionFilter("Settings", "properties")
            val fileChooser = JFileChooser()
            fileChooser.fileFilter = filter
            val option = fileChooser.showOpenDialog(SwingUtilities.getWindowAncestor(this) as JFrame)
            if (option == JFileChooser.APPROVE_OPTION) {
                val settingsController = SettingsController(fileChooser.selectedFile)
                settings = settingsController.readSettings()
            }
        }
        // Create the panel
        panel.border = EmptyBorder(5, 5, 5, 5)
        panel.layout = GridBagLayout()
        constraints.insets = Insets(5, 5, 5, 5)
        constraints.fill = GridBagConstraints.RELATIVE
        constraints.weightx = 1.0
        constraints.gridx = 0
        constraints.gridy = 0
        panel.add(colourThemeLabel, constraints)
        constraints.fill = GridBagConstraints.HORIZONTAL
        constraints.gridx = 1
        constraints.gridy = 0
        panel.add(colourThemeInput, constraints)
        constraints.fill = GridBagConstraints.RELATIVE
        constraints.gridx = 0
        constraints.gridy = 1
        panel.add(openaiKeyLabel, constraints)
        constraints.fill = GridBagConstraints.HORIZONTAL
        constraints.gridx = 1
        constraints.gridy = 1
        panel.add(openaiKeyInput, constraints)
        constraints.fill = GridBagConstraints.RELATIVE
        constraints.gridx = 0
        constraints.gridy = 2
        panel.add(chatLanguageLabel, constraints)
        constraints.fill = GridBagConstraints.HORIZONTAL
        constraints.gridx = 1
        constraints.gridy = 2
        panel.add(chatLanguageInput, constraints)
        constraints.gridx = 0
        constraints.gridy = 3
        panel.add(backupButton, constraints)
        constraints.gridx = 1
        constraints.gridy = 3
        panel.add(restoreButton, constraints)
        constraints.fill = GridBagConstraints.RELATIVE
        constraints.gridx = 0
        constraints.gridy = 4
        constraints.gridwidth = 2
        panel.add(restartMessage, constraints)
        constraints.fill = GridBagConstraints.BOTH
        constraints.weighty = 1.0
        constraints.gridx = 0
        constraints.gridy = 5
        panel.add(spacer, constraints)
        constraints.fill = GridBagConstraints.RELATIVE
        constraints.weighty = 0.0
        constraints.gridx = 0
        constraints.gridy = 6
        panel.add(versionLabel, constraints)
        constraints.fill = GridBagConstraints.HORIZONTAL
        constraints.gridx = 0
        constraints.gridy = 7
        constraints.gridwidth = 1
        panel.add(okButton, constraints)
        constraints.gridx = 1
        constraints.gridy = 7
        panel.add(cancelButton, constraints)
        // Create the dialog window
        size = Dimension(640, 640)
        minimumSize = Dimension(480, 480)
        defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
        contentPane = panel
        isResizable = true
        pack()
        setLocationRelativeTo(parent)
    }

    constructor(parent: JFrame, settings: Settings) : this(parent) {
        this.settings = settings
    }

    fun run(): Int {
        isVisible = true
        settings = Settings(
            EColourTheme.values()[colourThemeInput.selectedIndex],
            openaiKeyInput.text,
            EChatLanguage.values()[chatLanguageInput.selectedIndex]
        )
        return status
    }
}
