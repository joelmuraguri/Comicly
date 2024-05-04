package com.muraguri.comicly.features.explore.character

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muraguri.comicly.R
import com.muraguri.comicly.di.ViewModelFactory
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import androidx.lifecycle.viewmodel.compose.viewModel
import com.muraguri.comicly.core.domain.models.comics.CharacterInfo
import com.muraguri.comicly.core.domain.utils.Resource
import com.muraguri.comicly.core.remote.ComicsService
import com.muraguri.comicly.ui.connectivity.components.ErrorConnection

@Composable
fun CharacterInfoScreen(
    viewModel: CharacterViewModel = viewModel(factory = ViewModelFactory.Factory),
) {

    val selectedCharacter by viewModel.selectedCharacter.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when(selectedCharacter){
            is Resource.Failure -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                    .fillMaxWidth()
                 ) {
                    (selectedCharacter as Resource.Failure).error.localizedMessage?.let {
                        ErrorConnection(
                            message = it,
                            onRetry = {

                            }
                        )
                    }
            }
            }
            Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is Resource.Success -> {
                val character = (selectedCharacter as Resource.Success<CharacterInfo>).data
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    item {
                        Header(
                            characterImage = character.image,
                            deck = character.deck,
                            issueAppearance = character.issueAppearance ,
                            characterName = character.name,
                            publisherImage = character.publisher.image,
                        )
                    }
                }
            }
        }
    }

}


@Composable
fun Header(
    characterImage : String,
    deck : String,
    issueAppearance : Int,
    characterName : String,
    publisherImage : String,
    landscape: Boolean = false,
    modifier: Modifier = Modifier
){
    Row(
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ImageItemCard(
            characterImage = characterImage,
            modifier = Modifier
                .width(if (landscape) 215.dp else 130.dp)
                .height(if (landscape) 161.25.dp else 195.dp),
            landscape = landscape)

        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = characterName,
                fontSize = 26.sp,
                fontFamily = FontFamily(Font(R.font.open_sans_bold)),
                modifier =  modifier.padding(vertical = 10.dp)
            )
            Text(
                text = "$characterName appears in $issueAppearance issues",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.open_sans_light)),
                modifier =  modifier.padding(vertical = 10.dp)

            )
            HorizontalDivider()
            Text(
                text = deck,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.open_sans_light))
            )
            PublisherItemCard(
                publisherImage = publisherImage,
                modifier = Modifier
                    .width(if (landscape) 215.dp else 130.dp)
                    .height(if (landscape) 161.25.dp else 195.dp),
                landscape = landscape)
        }

    }
}

@Composable
fun PublisherItemCard(
    publisherImage: String,
    modifier: Modifier,
    landscape : Boolean
){

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(all = 4.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {

            },
        horizontalAlignment = Alignment.Start
    ) {
        CoilImage(
            imageModel = publisherImage,
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
            modifier = modifier.clip(CircleShape),
            contentDescription = null
        )
    }
}
@Composable
fun ImageItemCard(
    characterImage : String,
    modifier: Modifier,
    landscape : Boolean
){

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(all = 4.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {

            },
        horizontalAlignment = Alignment.Start
    ) {
        CoilImage(
            imageModel = characterImage,
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
            contentDescription = null
        )
    }
}

