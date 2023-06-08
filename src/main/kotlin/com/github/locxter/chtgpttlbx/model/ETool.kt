package com.github.locxter.chtgpttlbx.model

import com.aallam.openai.api.BetaOpenAI
import com.github.locxter.chtgpttlbx.lib.Tool
import com.github.locxter.chtgpttlbx.tools.*

@BetaOpenAI
enum class ETool(val displayName: String, val component: Tool) {
    TOOL_STANDARD_CHAT("Standard chat", StandardChat()),
    TOOL_OVERIEW_CREATOR("Overview creator", OverviewCreator()),
    TOOL_PRESENTATION_CREATOR("Presentation creator", PresentationCreator()),
    TOOL_TEXT_SUMMARIZER("Text summarizer", TextSummarizer()),
    TOOL_VIDEO_SUMMARIZER("Video summarizer", VideoSummarizer()),
    TOOL_TEXT_ANALYZER("Text analyzer", TextAnalyzer()),
    TOOL_VIDEO_ANALYZER("Video analyzer", VideoAnalyzer()),
}
