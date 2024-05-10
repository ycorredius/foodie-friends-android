package com.example.foodiefriends.ui.reusables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.foodiefriends.R

@Composable
fun ImageComposable(url: String?, userPhoto: Boolean = false) {
	if (url?.isNotEmpty() == true && userPhoto) {
		SubcomposeAsyncImage(
			model = ImageRequest.Builder(LocalContext.current)
				.data(url)
				.size(Size.ORIGINAL)
				.crossfade(true)
				.build(),
			contentDescription = null,
			contentScale = ContentScale.Crop,
			modifier = Modifier
				.size(75.dp)
				.padding(5.dp)
				.clip(CircleShape)
				.border(
					width = 1.dp,
					color = MaterialTheme.colorScheme.secondary,
					shape = CircleShape
				)
		)
	} else if (url?.isNotEmpty() == true) {
		SubcomposeAsyncImage(
			model = ImageRequest.Builder(LocalContext.current)
				.data(url)
				.size(Size.ORIGINAL)
				.crossfade(true)
				.build(),
			contentDescription = null,
			contentScale = ContentScale.None
		)
	} else {
		Image(
			modifier = Modifier
				.padding(10.dp)
				.size(60.dp)
				.border(
					width = 1.dp,
					color = MaterialTheme.colorScheme.secondary,
					shape = CircleShape
				),
			painter = painterResource(id = R.drawable.profile_user),
			contentDescription = "Default user profile",
			contentScale = ContentScale.Crop,
		)
	}
}

@Composable
fun ProfilePhoto(url: String?) {
	if (url?.isNotEmpty() == true) {
		SubcomposeAsyncImage(
			model = ImageRequest.Builder(LocalContext.current)
				.data(url)
				.size(Size.ORIGINAL)
				.crossfade(true)
				.build(),
			contentDescription = null,
			contentScale = ContentScale.Crop,
			modifier = Modifier
				.size(150.dp)
				.clip(CircleShape)
		)
	} else {
		Image(
			modifier = Modifier
				.clip(CircleShape)
				.size(100.dp),
			painter = painterResource(id = R.drawable.default_profile_photo),
			contentDescription = "Default Food image",
		)
	}
}

@Composable
fun CoverImage(url: String?) {
	if (url?.isNotEmpty() == true) {
		SubcomposeAsyncImage(
			model = ImageRequest.Builder(LocalContext.current)
				.data(url)
				.size(Size.ORIGINAL)
				.crossfade(true)
				.build(),
			contentDescription = null,
			contentScale = ContentScale.Crop,
			modifier = Modifier.height(300.dp)
		)
	} else {
		Image(
			modifier = Modifier.fillMaxWidth(),
			painter = painterResource(id = R.drawable.default_food),
			contentDescription = "Default food image",
			contentScale = ContentScale.FillWidth
		)
	}
}

@Composable
fun RecipeCardImage(
	url: String?
) {
	if (url?.isNotEmpty() == true) {
		SubcomposeAsyncImage(
			model = ImageRequest.Builder(LocalContext.current)
				.data(url)
				.size(Size.ORIGINAL)
				.crossfade(true)
				.build(),
			contentDescription = null,
			contentScale = ContentScale.FillBounds,
			modifier = Modifier
				.width(300.dp)
				.aspectRatio(16f / 9f)
		)
	} else {
		Image(
			painter = painterResource(id = R.drawable.default_food),
			contentDescription = "Default Food image",
			modifier = Modifier
				.width(300.dp)
				.aspectRatio(16f / 9f),
			contentScale = ContentScale.FillBounds
		)
	}
}