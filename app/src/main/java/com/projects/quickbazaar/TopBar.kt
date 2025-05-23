package com.projects.quickbazaar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.projects.quickbazaar.ui.theme.theme_blue
import com.projects.quickbazaar.ui.theme.theme_light_blue
import com.projects.quickbazaar.ui.theme.theme_orange


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(
    navController: NavHostController,
    isSearchMode: MutableState<Boolean>,

) {
    var searchQuery= remember {
        mutableStateOf("")
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(theme_orange)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically, // Aligns items vertically in the center
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (isSearchMode.value) {
            // Back button and search bar
            IconButton(onClick = { navController.navigate("home")
                isSearchMode.value=false}) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    tint = theme_light_blue,
                    modifier = Modifier.size(32.dp)
                )
            }

            TextField(
                value = searchQuery.value,
                trailingIcon = {
                    Icon(painter = painterResource(id = R.drawable.erase_icon),
                        contentDescription = "For erasing text",
                        modifier = Modifier.clickable { searchQuery.value="" })
                },
                onValueChange = { searchQuery.value=it },
                placeholder = { Text("Search") },
                modifier = Modifier // Makes TextField fill the height of the Row
                    .weight(1f) // Allows it to occupy remaining horizontal space
                    .padding(horizontal = 8.dp).border(3.dp, Color.Black, RoundedCornerShape(30)),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent, // Removes underline
                    focusedIndicatorColor = Color.Transparent // Removes underline
                ),
                singleLine = true,
                shape = RoundedCornerShape(30),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        navController.navigate("search/${searchQuery.value}")
                    }
                )
            )
        } else {
            // Regular TopBar
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = "Welcome,",
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = getUserName(getCurrentUserId()!!),
                    fontSize = 15.sp,
                    color = theme_blue,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.offset(0.dp, 0.dp)
                )
            }
            Row {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.offset(6.dp)
                ) {
                    IconButton(onClick = { isSearchMode.value = true
                    navController.navigate("searching")}) {
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
                ) {
                    IconButton(onClick = {  navController.navigate("accountManagement")}) {
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
}
