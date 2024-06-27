package com.example.oxcompose.activity.mainactivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.oxcompose.activity.mainactivity.GameViewModel
import com.example.oxcompose.ui.theme.OxcomposeTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oxcompose.activity.mainactivity.Model
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OxcomposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val gameViewModel: GameViewModel by viewModels()
                    ImageGrid(gameViewModel, Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Composable
    fun ImageGrid(viewModel: GameViewModel, modifier: Modifier = Modifier) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding) // Use the padding values here
                ) {
                    Text(
                        text = "Current Turn: ${viewModel.currentPlayer.value}",
                        modifier = Modifier.padding(16.dp),
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(20.dp)
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center // Center the content inside the Box
                    ) {
                        Text(text = viewModel.winnerString.value)
                    }
                }
            }
        )
    }

    @Composable
    fun ButtonRestart(viewModel: GameViewModel) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentAlignment = Alignment.Center // Center the content inside the Box
        ) {
            Button(
                onClick = {
                    viewModel.resetGame()
                },
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = "Restart",
                    color = Color.White
                ) // Change text color to white for better visibility
            }
        }
    }


    @Composable
    fun ClickableImage(
        imageItem: Model.ImageItem,
        onClick: () -> Unit,
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)  // Ensure the box is exactly 30dp by 30dp
                .border(2.dp, Color.Black)
                .clip(RoundedCornerShape(8.dp))
                .padding(2.dp)
                .clickable(enabled = !imageItem.clicked) {
                    onClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageItem.resId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)  // Ensure the image is exactly 30dp by 30dp
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}
