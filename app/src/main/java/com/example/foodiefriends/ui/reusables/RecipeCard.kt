package com.example.foodiefriends.ui.reusables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.foodiefriends.R
import com.example.foodiefriends.data.Recipe
import com.example.foodiefriends.printMsg

@Composable
fun RecipeCard(
	recipe: Recipe
) {
	ElevatedCard(
		modifier = Modifier
			.padding(5.dp)
			.fillMaxWidth()
			.background(Color.Transparent)
			.clickable { /* Does a thing to navigate to recipe show page */ },
		elevation = CardDefaults.cardElevation(
			defaultElevation = 5.dp
		),
	) {
		//TODO: Drawable default image breaks everything but doesn't crash.
		if (recipe.attributes.thumbnailUrl.isNullOrEmpty() || recipe.attributes.thumbnailUrl.isEmpty()) {
			Image(
				painter = painterResource(id = R.drawable.default_food),
				contentDescription = "Default Food image"
			)
		} else {
			printMsg("@@@@ This is recipe image_url: ${recipe.attributes.thumbnailUrl}")
			AsyncImage(
				model = ImageRequest.Builder(LocalContext.current)
					.data(recipe.attributes.thumbnailUrl)
					.build(), contentDescription = "Recipe image"
			)
		}
		Text(
			text = recipe.attributes.name,
			fontSize = 14.sp,
			modifier = Modifier.padding(10.dp),
			maxLines = 1,
			softWrap = true
		)
	}
}