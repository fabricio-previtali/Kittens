package com.example.kittens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.kittens.mock.DataSourceBreed
import com.example.kittens.model.Kitten
import com.example.kittens.network.RetrofitInstance.catApi
import com.example.kittens.ui.theme.KittensTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private val dataState = mutableStateOf<List<Kitten>>(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch(Dispatchers.IO) {
            getCats(dataState)
        }
        setContent {
            KittensTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    KittensApp(
                        modifier = Modifier.padding(innerPadding), dataState.value
                    )
                }
            }
        }
    }
}


@Composable
fun KittensApp(modifier: Modifier = Modifier, kittens: List<Kitten>) {

    Column(modifier.fillMaxSize()) {
        Row(modifier.fillMaxWidth()) {
            KittenAppName(modifier)
        }
        LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(3)) {
            items(kittens) { kitten -> KittenItem(kitten = kitten) }

        }
    }
}

@Composable
fun KittenAppName(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.app_name),
        modifier = modifier.padding(start = 24.dp, top = 16.dp, bottom = 16.dp),
        fontSize = 24.sp,
    )
}

@Composable
fun KittenItem(modifier: Modifier = Modifier, kitten: Kitten) {

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier
        ) {
            KittenImage(modifier)
            KittenFavourite(modifier.align(Alignment.TopEnd))
        }
        KittenBreed(modifier, kitten.name)

    }
}

@Composable
fun KittenImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = null,
        modifier = modifier.size(100.dp)
    )
}

@Composable
fun KittenBreed(modifier: Modifier, breed: String) {
    Text(
        text = breed,
        modifier = modifier,
        fontSize = 16.sp,
        textAlign = TextAlign.Center
    )
}

@Composable
fun KittenNonFavourite(modifier: Modifier) {
    Box {
        Icon(
            painter = painterResource(id = R.drawable.baseline_star_border_24),
            contentDescription = null,
            modifier = modifier
                .size(48.dp)
                .align(Alignment.TopEnd)
        )
    }
}

@Composable
fun KittenFavourite(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.baseline_star_24),
        contentDescription = null,
        modifier = modifier.size(32.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview(modifier: Modifier = Modifier) {
    KittensTheme {
        KittensApp(modifier, DataSourceBreed.breed)
    }
}

suspend fun getCats(dataState: MutableState<List<Kitten>>): List<Kitten> {
    val response = catApi.getKittens()
    if (response.isNotEmpty()) {
        withContext(Dispatchers.IO) {
            dataState.value = response
        }
    } else {

    }
    return dataState.value
}