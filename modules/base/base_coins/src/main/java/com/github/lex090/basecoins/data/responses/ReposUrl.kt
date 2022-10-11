package com.github.lex090.basecoins.data.responses

import com.squareup.moshi.Json

data class ReposUrl(
    @Json(name = "github")
    val github: List<String>?
)