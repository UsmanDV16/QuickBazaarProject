package com.projects.quickbazaar

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projects.quickbazaar.ui.theme.theme_blue

@Composable
fun LongButton(onClick: () -> Unit,
               enabled: Boolean = true,
               colors: ButtonColors = ButtonDefaults.buttonColors(),
               border: BorderStroke? = null,
               text: String
)

{
    Button(
        onClick = onClick, // Replace "login" with the route name for your login screen
        shape = RoundedCornerShape(60),
        colors = colors,
        border = border,
        modifier = Modifier
            .fillMaxWidth(0.83f)
            .height(40.dp)
    ) {
        Text(
            text = text,
            color = theme_blue,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,

        )
    }
}