package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LemonApp()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonApp() {
    var state by rememberSaveable {
        mutableStateOf(UiState.Tree)
    }
    var tapCount by rememberSaveable {
        mutableIntStateOf(0)
    }

    val nextState = when (state) {
        UiState.Tree -> UiState.Lemon
        UiState.Lemon -> UiState.Lemonade
        UiState.Lemonade -> UiState.EmptyGlass
        UiState.EmptyGlass -> UiState.Tree
    }

    val onClick: () -> Unit = when (state) {
        UiState.Tree -> { ->
            tapCount = (1..3).random()
            state = nextState
        }

        UiState.Lemon -> { ->
            if (tapCount == 0) {
                state = nextState
            }
            tapCount--
        }

        UiState.Lemonade,
        UiState.EmptyGlass -> { ->
            state = nextState
        }

    }

    val pictureId = when (state) {
        UiState.Tree -> R.drawable.lemon_tree
        UiState.Lemon -> R.drawable.lemon_squeeze
        UiState.Lemonade -> R.drawable.lemon_drink
        UiState.EmptyGlass -> R.drawable.lemon_restart
    }

    val imageDescriptionId = when (state) {
        UiState.Tree -> R.string.tree
        UiState.Lemon -> R.string.lemon
        UiState.Lemonade -> R.string.lemonade
        UiState.EmptyGlass -> R.string.empty
    }

    val textLabelId = when (state) {
        UiState.Tree -> R.string.select
        UiState.Lemon -> R.string.squeeze
        UiState.Lemonade -> R.string.drink
        UiState.EmptyGlass -> R.string.again
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Lemonade", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF9E44C)
                )
            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(
                        color = Color(0xFFC3ECD2),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable(
                        onClick = onClick
                    )
            ) {
                Image(
                    painter = painterResource(id = pictureId),
                    contentDescription = stringResource(id = imageDescriptionId),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(200.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = textLabelId))

        }
    }
}

enum class UiState {
    Tree,
    Lemon,
    Lemonade,
    EmptyGlass
}
