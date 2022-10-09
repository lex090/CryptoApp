package com.github.lex090.basecoins.data.responses

import com.squareup.moshi.Json

data class Image(
    @Json(name = "small")
    val small: String = "",
    @Json(name = "large")
    val large: String = "",
    @Json(name = "thumb")
    val thumb: String = ""
)