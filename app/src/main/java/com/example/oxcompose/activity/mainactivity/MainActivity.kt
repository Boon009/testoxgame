package com.example.oxcompose.activity.mainactivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.oxcompose.ui.theme.OxcomposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OxcomposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val gameViewModel: GameViewModel by viewModels()
                    CreateImageGrid(gameViewModel, Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Suppress("ktlint:standard:function-naming")
    @Composable
    fun CreateImageGrid(
        viewModel: GameViewModel,
        modifier: Modifier = Modifier,
    ) {
        val timeLeft = viewModel.timeLeft.collectAsState()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            content = { innerPadding ->
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                    // .background(Color.LightGray)
                ) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                    ) {
                        Text(
                            text = "Current Turn: ${viewModel.currentPlayer.value}",
                            modifier = Modifier.weight(1f),
                        )

                        Text(
                            text = "Time left: ${timeLeft.value}",
                            modifier = Modifier.weight(1f),
                        )
                    }
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier =
                            Modifier
                                .padding(16.dp),
                        // .weight(1f)
                    ) {
                        items(viewModel.imageList.size) { index ->
                            val imageItem = viewModel.imageList[index]

                            ClickableImage(imageItem = imageItem, onClick = {
                                viewModel.handleClick(index)
                            })
                        }
                    }

                    ButtonRestart(viewModel)
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = viewModel.winnerString.value)
                    }
                }
            },
        )
    }

    @Suppress("ktlint:standard:function-naming")
    @Composable
    fun ButtonRestart(viewModel: GameViewModel) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
            contentAlignment = Alignment.Center,
        ) {
            Button(
                onClick = {
                    viewModel.resetGame()
                },
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier.align(Alignment.Center),
            ) {
                Text(
                    text = "Restart",
                    color = Color.White,
                )
            }
        }
    }

    @Suppress("ktlint:standard:function-naming")
    @Composable
    fun ClickableImage(
        imageItem: Model.ImageItem,
        onClick: () -> Unit,
    ) {
        Box(
            modifier =
                Modifier
                    .size(60.dp)
                    .border(2.dp, Color.Black)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(2.dp)
                    .clickable(enabled = !imageItem.clicked) {
                        onClick()
                    },
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = imageItem.resId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp)),
            )
        }
    }
}
