package com.example.imagedisplay.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.imagedisplay.data.GalleryImage

@Composable
fun GalleryImageItem(image: GalleryImage) {
    AsyncImage(
        model = image.url,
        contentDescription = "Gallery Image",
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        contentScale = ContentScale.Fit,
    )
    Log.d("GalleryImageItem", "Image URL: ${image.url}")
}