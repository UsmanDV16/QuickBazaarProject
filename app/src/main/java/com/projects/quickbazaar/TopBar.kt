package com.projects.quickbazaar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.projects.quickbazaar.ui.theme.theme_blue
import com.projects.quickbazaar.ui.theme.theme_light_blue
import com.projects.quickbazaar.ui.theme.theme_orange


@Composable
fun TopBar(navController: NavHostController) {
    val currentRoute = currentRoute(navController)

    Row(
        modifier = Modifier
            .fillMaxWidth().height(70.dp)
            .background(theme_orange)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

            Column(
                horizontalAlignment = AbsoluteAlignment.Left,
                modifier = Modifier.fillMaxHeight()
            )
            {
                Text(
                    text = "Welcome,",
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "User!",
                    fontSize = 15.sp,
                    color = theme_blue,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.offset(0.dp, -5.dp)
                )
            }
            Row() {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.offset(6.dp)
                )
                {

                    IconButton(onClick = { /* Handle Search Click */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.search_icon),
                            contentDescription = "Search",
                            tint = theme_light_blue,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally

                )
                {

                    IconButton(onClick = { /* Handle Profile Click */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.profile_icon),
                            contentDescription = "Profile",
                            tint = theme_light_blue,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                }
            }



    }

}