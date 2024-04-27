package com.muraguri.comicly.ui.connectivity.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muraguri.comicly.R
import com.muraguri.comicly.ui.theme.ComiclyTheme

@Composable
fun NetworkUnavailable(
    onDismissRequest : () -> Unit
){

    Card(
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 5.dp
        ),
        border = BorderStroke(width = 0.5.dp, color = Color(0xFFD9901D)),
        modifier = Modifier
            .padding(horizontal = 50.dp)
            .clickable {
                onDismissRequest()
            },
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.round_wifi_off_24),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp),
                tint = Color(0xFFFEC708)
            )
            Text(
                text = stringResource(id = R.string.unavailable_internet),
                fontSize = 18.sp,
                color = Color(0xFFFEC708),
            )
            Text(
                text = stringResource(id = R.string.unavailable_internet_desc),
                fontSize = 12.sp,
                color = Color(0xFFD9901D),
            )
        }
    }
}


@Preview
@Composable
fun ConnectivityObserverLayoutPreview() {
    ComiclyTheme {
        NetworkUnavailable(
            onDismissRequest = {}
        )
    }
}