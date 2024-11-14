package com.example.kittens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.kittens.model.CatApiResponse
import com.example.kittens.network.RetrofitInstance.catApi
import com.example.kittens.ui.theme.KittensTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private val dataState = mutableStateOf<List<CatApiResponse>>(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch(Dispatchers.IO) {
            testGetCats(dataState)
        }
        setContent {
            KittensTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding),
                        cats = dataState.value
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier, cats: List<CatApiResponse>?) {

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(32.dp, 48.dp)) {
        Row(
            modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Cats App",
                modifier = modifier,
                fontSize = 24.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold

            )
        }
        Row(
            modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(horizontalAlignment = CenterHorizontally) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_star_border_24),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp).align(Alignment.TopEnd)
                    )
                }
                Text(
                    text = "abc",
                    modifier = modifier,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
            Column(horizontalAlignment = CenterHorizontally) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_star_border_24),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp).align(Alignment.TopEnd)
                    )
                }
                Text(
                    text = "abc",
                    modifier = modifier,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center

                )
            }
            Column(horizontalAlignment = CenterHorizontally) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_star_border_24),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp).align(Alignment.TopEnd)
                    )
                }
                Text(
                    text = "abc",
                    modifier = modifier,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center

                )
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KittensTheme {
        Greeting(cats = null)
    }
}

suspend fun testGetCats(dataState: MutableState<List<CatApiResponse>>): List<CatApiResponse> {
    val response = catApi.getCats()
    if (response.isNotEmpty()) {
        withContext(Dispatchers.Main) {
            dataState.value = response
        }
    } else {
        // Ainda não estudei como tratar os erros
    }
    return dataState.value
}