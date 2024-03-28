package com.example.jogodavelha

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jogodavelha.ui.theme.JogoDaVelhaTheme
import android.util.Log

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

@SuppressLint("RememberReturnType")
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var playerOption by remember {
        mutableStateOf("")
    }
    var player by remember {
        mutableStateOf(0)
    }

    val tabuleiro = remember {
        mutableStateListOf(
            mutableListOf("", "", ""),
            mutableListOf("", "", ""),
            mutableListOf("", "", "")
    )}

    val clickPlayer: () -> String = {
        // Alternar jogada
        playerOption = if (player == 0) "X" else "O"
        // Alternar jogador
        player = if (player == 0) 1 else 0
        // Retornar o novo valor de playerOption
        playerOption
    }

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(
                id = R.drawable.pexels_pixabay_326333
            ), contentDescription = null,
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ScoreBoard(player1, 0, player2, 23)
        tabuleiroVelha(tabuleiro = tabuleiro,playerOption=playerOption,clickPlayer = clickPlayer)
    }

}

@Composable
fun ScoreBoard(Player1: Player, draws: Int, Player2: Player, fontsize: Int) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Player 1:  ${Player1.vitorias} ", color = Color.Blue, fontSize = fontsize.sp)
        Text(text = "Draws: $draws", color = Color.White, fontSize = fontsize.sp)
        Text(text = "Player 2:  ${Player2.vitorias}", color = Color.Red, fontSize = fontsize.sp)
    }
}

@Composable
fun tabuleiroVelha(
    tabuleiro: List<MutableList<String>>,
    playerOption: String,
    clickPlayer: () -> String,
) {
    Column(modifier = Modifier.padding(0.dp, 16.dp)) {
        for (i in 0 until 3) {
            Row(modifier = Modifier) {
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
                            .clickable {
                                tabuleiro[i][j] = clickPlayer() },
                    ) {
                        Text(
                            text = tabuleiro[i][j],
                            fontSize = 90.sp,
                            color = if (tabuleiro[i][j] == "X") Color.Green else if (tabuleiro[i][j] == "O") Color.Yellow else Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                        )
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