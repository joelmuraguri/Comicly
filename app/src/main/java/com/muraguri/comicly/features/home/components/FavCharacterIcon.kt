package com.muraguri.comicly.features.home.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun FavCharacterIcon(
    imageUrl : String,
    imageSize : Dp,
    name : String
){

    CoilImage(
        imageModel = imageUrl,
        shimmerParams = ShimmerParams(
            baseColor = Color(0xFF180E36),
            highlightColor = Color(0XFF423460),
            durationMillis = 500,
            dropOff = 0.65F,
            tilt = 20F
        ),
        failure = {
        },
        contentScale = ContentScale.Crop,
        circularReveal = CircularReveal(duration = 1000),
        modifier = Modifier
            .border(2.dp, Color(0xFF5180f1), shape = CircleShape)
            .size(imageSize)
            .clip(CircleShape),
        contentDescription = name
    )

}


@Composable
fun AddFavCharacterCarousel(){
    Box(
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .border(2.dp, Color(0xFF1D1D2A), shape = CircleShape)
                .size(90.dp)
                .clip(CircleShape),
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
            )
        }
    }

}