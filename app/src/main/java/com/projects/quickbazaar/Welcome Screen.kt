package com.projects.quickbazaar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projects.quickbazaar.ui.theme.*

@Preview
@Composable
fun WelcomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo Image
        val logoPainter: Painter = painterResource(id = R.drawable.logo) // Replace `logo` with your actual logo image name
        Image(
            painter = logoPainter,
            contentDescription = "QuickBazaar Logo",
            modifier = Modifier
                .size(300.dp)
                .absoluteOffset(0.dp, 80.dp)
           // alignment=Alignment.TopCenter// Adjust size as needed
        )

        Spacer(modifier = Modifier.height(300.dp))

        // Sign Up Button

        LongButton(onClick = {  },
            colors = ButtonDefaults.outlinedButtonColors(),
            border = BorderStroke(3.dp, theme_blue),
            text = "Sign up", textColour = theme_blue)
        Spacer(modifier = Modifier.height(11.dp))

        // Log In Button
        LongButton(onClick = {  },
            colors = ButtonDefaults.buttonColors(containerColor = theme_orange),
            border = BorderStroke(3.dp, theme_blue),
            text = "Log in", textColour = theme_blue)
    }
}
