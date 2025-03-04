package com.example.kittens.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kittens.R
import com.example.kittens.model.CatByBreedResponseApi
import com.example.kittens.ui.theme.CatsTheme

@Composable
fun HomeScreen(
    catUiState: CatUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(8.dp),
    onCatClick: (CatByBreedResponseApi) -> Unit = {},
) {
    when (catUiState) {
        is CatUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is CatUiState.Success ->
            CatsGridScreen(
                catUiState.cats,
                contentPadding = contentPadding,
                modifier = modifier.fillMaxSize(),
                onCatClick = onCatClick,
            )
        is CatUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun CatsGridScreen(
    cats: List<CatByBreedResponseApi>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onCatClick: (CatByBreedResponseApi) -> Unit = {},
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        items(cats) { cat ->
            CatItem(cat = cat, onClick = { onCatClick(cat) })
        }
    }
}

@Composable
fun CatItem(
    cat: CatByBreedResponseApi,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Column(modifier.clickable(onClick = onClick), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier,
        ) {
            CatImage(catPhoto = cat, modifier = modifier)
        }
        CatBreed(modifier, cat.name)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircleLoading()
    }
}

@Composable
fun CircleLoading(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.size(128.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = null,
        )
        Text(
            text = stringResource(id = R.string.loading_failed),
            modifier = Modifier.padding(16.dp),
        )
        Button(onClick = retryAction) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
fun CatImage(
    catPhoto: CatByBreedResponseApi,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        AsyncImage(
            model =
                ImageRequest
                    .Builder(context = LocalContext.current)
                    .data(catPhoto.image?.url)
                    .crossfade(true)
                    .build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentScale = ContentScale.Crop,
            modifier = modifier.size(100.dp),
            contentDescription = null,
        )
    }
}

@Composable
fun CatBreed(
    modifier: Modifier,
    breed: String,
) {
    Text(
        text = breed,
        modifier = modifier,
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
    )
}

// @Composable
// fun KittenNonFavourite(modifier: Modifier) {
//    Box {
//        Icon(
//            painter = painterResource(id = R.drawable.baseline_star_border_24),
//            contentDescription = null,
//            modifier = modifier
//                .size(48.dp)
//                .align(Alignment.TopEnd)
//        )
//    }
// }

// @Composable
// fun KittenFavourite(modifier: Modifier) {
//    Image(
//        painter = painterResource(id = R.drawable.baseline_star_24),
//        contentDescription = null,
//        modifier = modifier.size(32.dp)
//    )
// }

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    CatsTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    CatsTheme {
        ErrorScreen({})
    }
}

@SuppressLint("ResourceType")
@Preview(showBackground = true)
@Composable
fun CatsGridScreenPreview() {
    CatsTheme {
        val mockData =
            List(10) {
                CatByBreedResponseApi(
                    "bengi",
                    "test",
                    "Brazil",
                    "test",
                    countryCode = "BR",
                )
            }
        CatsGridScreen(mockData)
    }
}
