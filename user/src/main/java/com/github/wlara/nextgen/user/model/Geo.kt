package com.github.wlara.nextgen.user.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Geo(
    @SerialName("lat") val latitude: Double,
    @SerialName("lng") val longitude: Double
)