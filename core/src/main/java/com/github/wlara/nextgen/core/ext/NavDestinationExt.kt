package com.github.wlara.nextgen.core.ext

import androidx.navigation.NavDestination

val NavDestination.isStart: Boolean
    get() = id == parent?.startDestinationId