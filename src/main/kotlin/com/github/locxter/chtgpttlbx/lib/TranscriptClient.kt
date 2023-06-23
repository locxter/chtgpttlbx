package com.github.locxter.chtgpttlbx.lib

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.github.locxter.chtgpttlbx.model.EChatLanguage
import com.github.locxter.chtgpttlbx.model.Transcript
import com.github.locxter.chtgpttlbx.model.VideoId

class TranscriptClient {
    companion object {
        fun getTranscript(videoId: VideoId, language: EChatLanguage): Transcript {
            val transcript = Transcript(videoId, language, "")
            val (request, response, result) = Fuel.get(
                when (language) {
                    EChatLanguage.CHAT_LANGUAGE_ENGLISH -> "https://getsubs.cc/get_y.php?i=${videoId.id}&format=txt&hl=en&a=auto"
                    EChatLanguage.CHAT_LANGUAGE_GERMAN -> "https://getsubs.cc/get_y.php?i=${videoId.id}&format=txt&hl=de&a=auto"
                }
            ).responseString()
            when (result) {
                is Result.Failure -> {
                    println(result.getException())
                }

                is Result.Success -> {
                    transcript.content =
                        response.body().asString("text/plain; charset=utf-8").replace(Regex("[\r\n]+"), "\n")
                }
            }
            return transcript
        }
    }
}
