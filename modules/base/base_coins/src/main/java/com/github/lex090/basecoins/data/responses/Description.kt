package com.github.lex090.basecoins.data.responses

import com.squareup.moshi.Json

data class Description(
    @Json(name = "en")
    val en: String = ""
)