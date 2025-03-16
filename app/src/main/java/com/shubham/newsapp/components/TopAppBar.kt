package com.shubham.newsapp.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar

import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.shubham.newsapp.BottomMenuScreens
import com.shubham.newsapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarUI(navController: NavController) {
    TopAppBar(
        title = {
            Text("Details Screen", fontWeight = FontWeight.SemiBold)
        },
        navigationIcon = {
            IconButton(onClick = {
                // Using navController to navigate to TopNews Screen
                navController.navigate(BottomMenuScreens.TopNews.route)
            }) {
                Icon(Icons.Default.ArrowBack, "Back")
            }
        },
        colors = TopAppBarColors(
            containerColor = colorResource(R.color.purple_500),
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White,
            scrolledContainerColor = Color.Transparent,
            actionIconContentColor = Color.Transparent
        )
    )
}