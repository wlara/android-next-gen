package com.github.wlara.nextgen.nextgen.core.ext

import androidx.navigation.NavDestination

val NavDestination.isStart: Boolean
    get() = id == parent?.startDestinationId