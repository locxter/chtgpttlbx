package com.github.locxter.chtgpttlbx.model

data class Transcript(
    val videoId: VideoId = VideoId(),
    val language: EChatLanguage = EChatLanguage.CHAT_LANGUAGE_ENGLISH,
    var content: String = ""
)
