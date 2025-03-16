package com.shubham.newsapp.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shubham.newsapp.R
import com.shubham.newsapp.network.NewsManager

@Composable
fun SearchBar(query: MutableState<String>, newsManager: NewsManager) {
    val localFocusManager = LocalFocusManager.current

    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(colorResource(R.color.purple_500))
    ) {
        TextField(
            value = query.value,
            onValueChange = {
                query.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
            label = {Text("Search News", color = Color.White)},
            colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent),
            leadingIcon = {Icon(Icons.Filled.Search, "Search", tint = Color.White)},
            trailingIcon = {
                if(query.value != "") {
                    IconButton(onClick = {
                        query.value = ""
                        localFocusManager.clearFocus()
                    }) {
                        Icon(Icons.Default.Clear, "Clear", tint = Color.White)
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search),           // Shows Search Icon in Keyboard
            keyboardActions = KeyboardActions(
                onSearch = {
                    if(query.value != "") {
                        newsManager.getArticlesByQuery(query.value)
                        localFocusManager.clearFocus()
                    }
                },
            )
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchBarPreview(modifier: Modifier = Modifier) {
    SearchBar(mutableStateOf(""), NewsManager())
}