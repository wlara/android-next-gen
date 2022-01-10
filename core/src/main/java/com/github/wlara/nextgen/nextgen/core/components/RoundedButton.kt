package com.github.wlara.nextgen.nextgen.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoundedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(2.dp, MaterialTheme.colors.primary),
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colors.primary,
            backgroundColor = MaterialTheme.colors.background
        )
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 6.dp)
        )
    }
}