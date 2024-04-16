package com.example.foodiefriends.ui.reusables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.foodiefriends.R

@Composable
fun RecipeSearchBar(
	getRecipes: (String) -> Unit = {}
) {
	var name by remember { mutableStateOf("") }
	Row(
		horizontalArrangement = Arrangement.Center,
		modifier = Modifier.padding(0.dp, 5.dp)
	) {
		OutlinedTextField(
			value = name,
			singleLine = true,
			shape = MaterialTheme.shapes.extraLarge,
			enabled = true,
			onValueChange = {
				name = it
			},
			placeholder = {
				Text(
					text = "Find Recipes",
				)
			},
			leadingIcon = {
				Icon(
					painter = painterResource(id = R.drawable.find_icon),
					contentDescription = "Search Icon",
					modifier = Modifier.size(20.dp)
				)
			},
			keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
			keyboardActions = KeyboardActions(
				onSearch = { getRecipes(name) }
			)
		)
	}
}
