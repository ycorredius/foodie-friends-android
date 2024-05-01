package com.example.foodiefriends.ui.reusables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.foodiefriends.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeSearchBar(
	getRecipes: (String) -> Unit = {},
	updateQuery: (String) -> Unit = {},
	query: String
) {
	val controller = LocalSoftwareKeyboardController.current
	var active by remember {
		mutableStateOf(false)
	}
	val filters = remember {
		mutableStateListOf(
			"Dinner",
			"Desert",
			"Breakfast"
		)
	}
	SearchBar(
		modifier = Modifier.padding(0.dp, 5.dp),
		query = query,
		onQueryChange = {
			updateQuery(it)
		},
		onSearch = {
			controller?.hide()
			getRecipes(it)
		},
		active = active,
		onActiveChange = {
			active = it
		},
		leadingIcon = {
			Icon(
				painter = painterResource(id = R.drawable.find_icon),
				contentDescription = "Search Icon",
				modifier = Modifier.size(20.dp)
			)
		},
		trailingIcon = {
			if (active) IconButton(onClick = {
				if (query.isNotEmpty()) {
					updateQuery("")
				} else {
					active = false
				}
			}) {

				Icon(
					imageVector = Icons.Default.Clear,
					contentDescription = "Clear filter"
				)
			}
		},
	) {
		LazyRow(
			modifier = Modifier
				.fillMaxWidth()
				.padding(5.dp, 0.dp),
			horizontalArrangement = Arrangement.spacedBy(5.dp)
		) {
			items(filters) {
				OutlinedButton(onClick = { /*TODO*/ }) {
					Text(text = it)
				}
			}
		}
	}
}
