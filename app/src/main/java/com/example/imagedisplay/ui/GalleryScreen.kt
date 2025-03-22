package com.example.imagedisplay.ui

import android.content.res.Configuration
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imagedisplay.GalleryViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen() {
    // Get the ViewModel
    val viewModel: GalleryViewModel = viewModel()

    // Collecting state from the ViewModel
    val galleryImages by viewModel.galleryImages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()


    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val columns = remember(isLandscape) {
        if (isLandscape) 2 else 1
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = "     (ᵔᴥᵔ)              Dog Gallery              (ᵔᴥᵔ)          Dog Gallery      (ᵔᴥᵔ)              Dog Gallery              (ᵔᴥᵔ)          Dog Gallery         ",
                    modifier = Modifier.basicMarquee()

                ) }, modifier = Modifier.height(50.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .paddingFromBaseline(top = 15.dp)
                .padding(innerPadding)
                .fillMaxHeight()// Apply padding from Scaffold
        ) {
            // LazyColumn for displaying images
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
            ) {
                items(galleryImages) { image ->
                    GalleryImageItem(image = image)
                }

                // Button to load more images
                item {
                    Button(
                        onClick = { viewModel.loadMoreImages() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally),
                        enabled = !isLoading // Disable the button while loading
                    ) {
                        Text(text = "Load More")
                    }
                }
            }
        }
    }
}
