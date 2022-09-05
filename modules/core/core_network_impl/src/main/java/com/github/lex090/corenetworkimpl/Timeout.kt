package com.github.lex090.corenetworkimpl

import java.util.concurrent.TimeUnit

internal data class Timeout(
    val timeout: Long,
    val unit: TimeUnit
)