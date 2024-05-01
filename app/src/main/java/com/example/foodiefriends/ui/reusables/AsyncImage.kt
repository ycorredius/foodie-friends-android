package com.example.foodiefriends.ui.reusables

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun ImageComposable(url: String?) {
	SubcomposeAsyncImage(
		model = ImageRequest.Builder(LocalContext.current)
			.data(url)
			.size(Size.ORIGINAL)
			.crossfade(true)
			.build(),
		contentDescription = null,
		contentScale = ContentScale.None
	)
}