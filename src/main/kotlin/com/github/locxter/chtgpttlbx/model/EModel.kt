package com.github.locxter.chtgpttlbx.model

import com.aallam.openai.api.model.ModelId

enum class EModel(val displayName: String, val modelId: ModelId, val maxContext: Int) {
    GPT_35_TURBO("gpt-3.5-turbo", ModelId("gpt-3.5-turbo"), 12500),
    GPT_35_TURBO_16K("gpt-3.5-turbo-16k", ModelId("gpt-3.5-turbo-16k"), 50000),
    GPT_4("gpt-4", ModelId("gpt-4"), 25000),
    GPT_4_32K("gpt-4-32k", ModelId("gpt-4-32k"), 100000)
}
