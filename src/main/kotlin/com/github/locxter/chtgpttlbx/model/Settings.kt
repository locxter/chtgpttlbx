package com.github.locxter.chtgpttlbx.model

data class Settings(
    val colourTheme: EColourTheme = EColourTheme.COLOUR_THEME_DARK,
    val openaiKey: String = "",
    val chatLanguage: EChatLanguage = EChatLanguage.CHAT_LANGUAGE_ENGLISH,
    val model: EModel = EModel.GPT_35_TURBO
)
