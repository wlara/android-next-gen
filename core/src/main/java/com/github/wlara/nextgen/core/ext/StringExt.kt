package com.github.wlara.nextgen.core.ext

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun String.encodeUrl(): String = URLEncoder.encode(this, StandardCharsets.UTF_8.toString())
