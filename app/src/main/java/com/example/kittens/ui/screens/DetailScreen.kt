package com.example.kittens.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.kittens.R
import com.example.kittens.model.CatByBreedResponseApi
import com.example.kittens.ui.theme.CatsTheme

@Composable
fun CatDetailScreen(
    cat: CatByBreedResponseApi,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(8.dp))
        Box {
            AsyncImage(
                model = cat.image?.url,
                contentDescription = "${cat.name} image",
                modifier =
                    Modifier
                        .size(250.dp)
                        .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = getFlagEmoji(cat.countryCode),
                fontSize = 50.sp,
                modifier =
                    Modifier
                        .padding(top = 4.dp)
                        .align(alignment = Alignment.BottomEnd),
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        HeadText(cat.name)
        Text(
            text = cat.description,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 4.dp, start = 16.dp, end = 16.dp),
        )
        Spacer(modifier = Modifier.padding(8.dp))
        HeadText("Temperament")
        Temperament(temperaments = cat.temperament.split(",").map { it.trim() })
    }
}

@Composable
fun HeadText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        modifier = modifier,
    )
}

@Composable
fun Temperament(
    temperaments: List<String>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        temperaments.forEach { temperament ->
            Card(
                modifier =
                    Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box {
                        Image(
                            painter = painterResource(id = getTemperamentDrawable(temperament)),
                            contentDescription = temperament,
                            modifier = Modifier.size(32.dp),
                        )
                    }
                    Text(
                        text = temperament,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(start = 16.dp),
                    )
                }
            }
        }
    }
}

private fun getTemperamentDrawable(temperament: String): Int =
    when (temperament.lowercase()) {
        "alert" -> R.drawable.temperament_alert
        "agile" -> R.drawable.temperament_agile
        "energetic" -> R.drawable.temperament_energetic
        "demanding" -> R.drawable.temperament_demanding
        "intelligent" -> R.drawable.temperament_intelligent
        else -> R.drawable.beng
    }

private fun getFlagEmoji(countryCode: String): String {
    if (countryCode.length != 2 || !countryCode.all { it.isUpperCase() }) {
        return "Invalid Code!"
    }
    val firstChar = countryCode[0]
    val secondChar = countryCode[1]

    val base = 0x1F1E6 - 'A'.code
    val firstIndicator = base + firstChar.code
    val secondIndicator = base + secondChar.code

    return String(Character.toChars(firstIndicator)) + String(Character.toChars(secondIndicator))
}

@Preview(showBackground = true)
@Composable
fun TemperamentPreview() {
    Temperament(
        temperaments =
            listOf(
                "Alert",
                "Agile",
                "Energetic",
                "Demanding",
                "Intelligent",
                "Intelligent",
                "Intelligent",
                "Intelligent",
                "Energetic",
            ),
    )
}

@Preview(showBackground = true)
@Composable
fun CatDetailScreenPreview() {
    CatsTheme {
        CatDetailScreen(
            cat =
                CatByBreedResponseApi(
                    name = "Abyssinian",
                    temperament = "Active, Energetic, Independent, a, b, c, d, e",
                    origin = "Brazil",
                    countryCode = "BR",
                    description = "The Abyssinian is easy to care for... hgdasudfgsajychjabchjabjcvajcvyasvcyuavscyvasghcvascvausy",
                    image =
                        com.example.kittens.model
                            .CatImage(url = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg"),
                ),
            modifier = Modifier,
        )
    }
}
