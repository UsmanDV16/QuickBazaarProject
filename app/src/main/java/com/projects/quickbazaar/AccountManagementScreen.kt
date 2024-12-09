package com.projects.quickbazaar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.projects.quickbazaar.ui.theme.theme_blue


@Composable
fun AccountManagementScreen(navController:NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Back Icon
            Icon(
                painter = painterResource(id = R.drawable.ic_back), // Replace with your back icon resource
                contentDescription = "Back",
                tint = theme_blue,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(10.dp)
                    .size(45.dp)
                    .offset(-2.dp, 0.dp)
                    .clickable { navController.popBackStack() }
            )


            Text(
                text = "YOUR ACCOUNT",
                fontSize = 60.sp,
                fontWeight = FontWeight.ExtraBold,
                color = theme_blue,
                textAlign = TextAlign.Left,
                modifier = Modifier.offset(12.dp, -10.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))

            Image(
                painter = painterResource(id = R.drawable.profile_icon),
                contentDescription = "Profile Icon",
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Muhammad Usman",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )

            Text(
                text = "myemail@gmail.com",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color(0xFF1A237E),
                    textDecoration = TextDecoration.Underline
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.info_update),
                        contentDescription = "Update Personal Info",
                        modifier = Modifier.size(130.dp)
                            .clickable { navController.navigate("infoUpdate") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.change_pass),
                        contentDescription = "Change Password",
                        modifier = Modifier.size(130.dp)
                            .clickable{navController.navigate("changePass/${getCurrentUserId()}")}
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                }
            }
            Row(horizontalArrangement = Arrangement.Absolute.Center){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable { navController.navigate("orderHistory/${getCurrentUserId()}") }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.orders_history),
                        contentDescription = "Your Previous Orders",
                        modifier = Modifier.size(130.dp)
                    )

                }
            }
        }
    }
}
