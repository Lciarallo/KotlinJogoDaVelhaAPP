package com.example.jogodavelha

import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jogodavelha.ui.theme.JogoDaVelhaTheme
import  com.example.jogodavelha.Player

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JogoDaVelhaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
val player1 = Player(0)
val player2 = Player(0)

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.pexels_pixabay_326333
            ), contentDescription = null,
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.Crop)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ScoreBoard(player1, 0, player2, 23)
        tabuleiroVelha()
    }


}

@Composable
fun ScoreBoard(Player1: Player, draws: Int, Player2: Player, fontsize: Int){
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
        Text(text = "Player 1:  ${Player1.vitorias} " , color = Color.Blue, fontSize = fontsize.sp)
        Text(text = "Draws: $draws", color = Color.White, fontSize = fontsize.sp)
        Text(text = "Player 2:  ${Player2.vitorias}", color = Color.Red, fontSize = fontsize.sp)
    }
}

@Composable
fun tabuleiroVelha() {
    Box(modifier = Modifier.fillMaxSize().padding(30.dp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            for (i in 0 until 3) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (j in 0 until 3) {
                        val maxWidth = when (j) {
                            0 -> 0.33f
                            1 -> 0.50f
                            else -> 1f
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(maxWidth)
                                .border(
                                    border = BorderStroke(
                                        color = Color.Red,
                                        width = 5.dp
                                    )
                                )
                        ) {

                            Text(text = " x", fontSize = 90.sp, color = Color.White,
                                textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JogoDaVelhaTheme {
        MainScreen()
    }
}