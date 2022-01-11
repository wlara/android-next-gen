package com.github.wlara.nextgen.core.ext

import io.ktor.client.features.*
import java.net.UnknownHostException

val Throwable.friendlyMessage: String
    get() = when (this) {
        is UnknownHostException -> "Server unavailable or no internet"
        is RedirectResponseException -> "Unhandled server redirection"
        is ServerResponseException -> "Internal server error"
        is ClientRequestException -> "Invalid client request"
        else -> javaClass.canonicalName ?: javaClass.simpleName
    }