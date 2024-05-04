package com.muraguri.comicly.features.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.muraguri.comicly.core.domain.models.comics.Issue
import com.muraguri.comicly.core.local.entity.FavCharacter
import com.muraguri.comicly.utils.trimTitle
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun FavCharacterIcon(
    favCharacter: FavCharacter,
    imageSize : Dp,
    name : String,
    onClick: (FavCharacter) -> Unit
){

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(all = 4.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick(favCharacter)
            },
        horizontalAlignment = Alignment.Start
    ) {
        CoilImage(
            imageModel = favCharacter.image,
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

@Composable
fun IssueItem(
    modifier: Modifier,
    landscape: Boolean,
    issues: Issue,
    onClick : (Issue) -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(all = 4.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick(issues)
            },
        horizontalAlignment = Alignment.Start
    ) {
        CoilImage(
            imageModel = issues.image,
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
            modifier = modifier.clip(RoundedCornerShape(8.dp)),
            contentDescription = "Movie item"
        )



        AnimatedVisibility(visible = landscape) {

            Text(
                text = trimTitle(issues.title),
                modifier = Modifier
                    .padding(start = 4.dp, top = 4.dp),
                maxLines = 1,
                color = Color.White,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            )
        }
    }
}


@Composable
fun IssueItemCard(
    issues: Issue,
    onClick : (Issue) -> Unit
){

    Card(
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 5.dp
        ),
        modifier = Modifier
            .padding(12.dp)
            .clickable {
                onClick(issues)
            },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CoilImage(
                imageModel = issues.image,
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
                    .clip(RoundedCornerShape(8.dp)),
                contentDescription = "Movie item"
            )

            Text(
                text = issues.title,
                color = Color.White,
                modifier = Modifier
                    .background(Color(0x80000000))
                    .padding(8.dp),
                fontWeight = FontWeight.ExtraBold
            )
        }
    }

}