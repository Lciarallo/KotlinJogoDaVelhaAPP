package com.example.jogodavelha

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.font.FontWeight

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

@SuppressLint("RememberReturnType")
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var wins by remember {
        mutableIntStateOf(0)
    }
    var draws by remember {
        mutableIntStateOf(0)
    }
    var losses by remember {
        mutableIntStateOf(0)
    }
    var playerOption by remember {
        mutableStateOf("")
    }
    var player by remember {
        mutableIntStateOf(0)
    }
    var optionInvalCount by remember {
        mutableStateOf(false)
    }

    val tabuleiro = remember {
        mutableStateListOf(
            mutableListOf("", "", ""),
            mutableListOf("", "", ""),
            mutableListOf("", "", "")
        )
    }
    val optionInvalCountAlter: () -> Unit = {
        optionInvalCount = optionInvalCount != true
    }

    val clickPlayer: () -> String = {
        optionInvalCount=false
        // Alternar jogada
        playerOption = if (player == 0) "X" else "O"
        // Alternar jogador
        player = if (player == 0) 1 else 0
        // Retornar o novo valor de playerOption
        playerOption
    }

    fun checkWin(tabuleiro: List<List<String>>, playerOption: String): Boolean {
        // Verificar linhas
        for (row in tabuleiro) {
            if (row.all { it == playerOption }) {
                return true
            }
        }
        // Verificar colunas
        for (i in tabuleiro.indices) {
            if (tabuleiro.all { it[i] == playerOption }) {
                return true
            }
        }
        // Verificar diagonais
        return (tabuleiro[0][0] == playerOption && tabuleiro[1][1] == playerOption && tabuleiro[2][2] == playerOption) ||
                (tabuleiro[0][2] == playerOption && tabuleiro[1][1] == playerOption && tabuleiro[2][0] == playerOption)
    }
    fun checkDraw(tabuleiro: List<List<String>>): Boolean {
        for (row in tabuleiro) {
            for (element in row) {
                if (element.isEmpty()) {
                    // Ainda há espaços vazios, então o jogo não é um empate
                    return false
                }
            }
        }
        // Se não houver espaços vazios e ninguém ganhou, é um empate
        return true
    }
    fun resetBoard(tabuleiro: MutableList<MutableList<String>>) {
        for (row in tabuleiro) {
            for (index in row.indices) {
                row[index] = "" // Define a posição como vazia
            }
        }
        playerOption = ""
        player = 0
        optionInvalCount = false
    }

    // Verificar se o jogador atual venceu após a jogada
    if (checkWin(tabuleiro, playerOption)) {
        if (playerOption == "X") {
            wins++
            resetBoard(tabuleiro)
        } else if (playerOption == "O") {
            losses++
            resetBoard(tabuleiro)
        }
    } else if(checkDraw(tabuleiro)) {
            draws++
            resetBoard(tabuleiro)
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
        ScoreBoard(wins= wins, draws= draws, losses= losses)
        TabuleiroVelha(tabuleiro = tabuleiro, optionInvalCountAlter = optionInvalCountAlter, clickPlayer = clickPlayer)
        if (optionInvalCount) {
            OptionInval(player)
        }
        PlayBoard(player)
    }

}

@Composable
fun ScoreBoard(wins: Int, draws: Int, losses: Int) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Player 1:  $wins", color = Color.Blue, fontSize = 23.sp)
        Text(text = "Draws: $draws", color = Color.White, fontSize = 23.sp)
        Text(text = "Player 2:  $losses", color = Color.Red, fontSize = 23.sp)
    }
}
@Composable
fun PlayBoard(player:Int) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Jogada do jogador:  ${player+1} ",
            color = if (player == 0) Color.Blue else if (player == 1) Color.Red else Color.White,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold)
    }
}

@Composable
fun OptionInval(player:Int) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Jogada invalida! Por favor escolha novamente.",
            color = if (player == 0) Color.Blue else if (player == 1) Color.Red else Color.White,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold)
    }
}

@Composable
fun TabuleiroVelha(
    tabuleiro: List<MutableList<String>>,
    optionInvalCountAlter:  () -> Unit,
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
                                    color = Color.White,
                                    width = 5.dp
                                )
                            )
                            .clickable {
                                if (tabuleiro[i][j] == "") {
                                    tabuleiro[i][j] = clickPlayer()
                                } else {
                                    optionInvalCountAlter()
                                }
                            },
                    ) {
                        Text(
                            text = tabuleiro[i][j],
                            fontSize = 90.sp,
                            color = if (tabuleiro[i][j] == "X") Color.Blue else if (tabuleiro[i][j] == "O") Color.Red else Color.White,
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