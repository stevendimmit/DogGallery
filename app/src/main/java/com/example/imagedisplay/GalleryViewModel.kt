package com.example.imagedisplay

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagedisplay.data.GalleryImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class GalleryViewModel : ViewModel() {

    private val _galleryImages = MutableStateFlow<List<GalleryImage>>(emptyList())
    val galleryImages: StateFlow<List<GalleryImage>> = _galleryImages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        fetchImages()
    }

    private fun fetchImages() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Fetch a set of new images
                val newImages = getDogImages(Random.nextInt(1, 5) + Random.nextInt(1,5)) // Random page offset
                _galleryImages.value = newImages
            } catch (e: Exception) {
                Log.e("GalleryViewModel", "Error loading images: ${e.message}", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadMoreImages() {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Load more images when called
                val newImages = getDogImages(Random.nextInt(1, 10)) // Random offset
                _galleryImages.value = _galleryImages.value + newImages
            } catch (e: Exception) {
                Log.e("GalleryViewModel", "Error loading more images: ${e.message}", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getDogImages(pageOffset: Int): List<GalleryImage> {
        val start = (pageOffset - 1) * 20
        Log.d("GalleryViewModel", "Fetching dog images with page offset: $pageOffset")
        return (start until start + 20).map {
            GalleryImage(
                id = it.toString(),
                url = "https://placedog.net/500/280?id=$it"
            )
        }
    }
}
