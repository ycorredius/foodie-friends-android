package com.example.foodiefriends.ui.reusables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodiefriends.AppState
import com.example.foodiefriends.R
import com.example.foodiefriends.data.Recipe

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecipeCard(
	recipe: Recipe,
	appState: AppState
) {
	ElevatedCard(
		modifier = Modifier
			.background(Color.Transparent)
			.padding(0.dp, 5.dp)
			.clickable {
				//TODO: rethink how I navigate to recipe details.
				appState.navController.navigate("recipe/${recipe.attributes.id}")
			},
		elevation = CardDefaults.cardElevation(
			defaultElevation = 2.dp
		),
		colors = CardDefaults.cardColors()
	) {
		RecipeCardImage(url = recipe.attributes.jumboUrl)
		Column(
			modifier = Modifier.padding(20.dp, 5.dp),
			verticalArrangement = Arrangement.spacedBy(2.dp)
		) {
			Text(
				text = recipe.attributes.name,
				fontSize = 18.sp,
				fontWeight = FontWeight.SemiBold,
				maxLines = 1,
				softWrap = true
			)
			Text(
				text = stringResource(
					id = R.string.recipe_user,
					recipe.attributes.user.firstName,
					recipe.attributes.user.lastName
				),
				fontSize = 16.sp,
				maxLines = 1,
				softWrap = true
			)
		}
	}
}