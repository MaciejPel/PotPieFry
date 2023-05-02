package com.potpiefry.ui.view

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.potpiefry.R
import com.potpiefry.data.dishes
import com.potpiefry.ui.viewmodel.NavigationViewModel


@Composable
fun DetailsScreen(
	navController: NavController,
	navigationViewModel: NavigationViewModel,
	dishId: Int,
) {
	val dish = dishes.find { dish -> dish.id == dishId }
	if (dish == null) {
		navController.navigate(NavigationScreen.Home.route)
		return
	}
	navigationViewModel.setNavigation(dish.name, NavigationScreen.Details.route)
	navigationViewModel.setDish(dish.id)

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		Card() {
			Column(modifier = Modifier.fillMaxWidth()) {
				AsyncImage(
					modifier = Modifier
						.fillMaxWidth()
						.aspectRatio(1.0f),
					model = ImageRequest.Builder(LocalContext.current)
						.data(dish.img)
						.crossfade(true)
						.build(),
					placeholder = painterResource(R.drawable.placeholder),
					contentDescription = null,
					contentScale = ContentScale.Crop,
				)
				Column(
					modifier = Modifier
						.padding(8.dp)
				) {
					Text(text = dish.name, fontSize = MaterialTheme.typography.headlineMedium.fontSize)
					Text(text = dish.description, fontStyle = MaterialTheme.typography.bodySmall.fontStyle)
				}
			}
		}
	}
}

@Composable
fun shareButton(dishId: Int?) {
	val dish = dishes.find { dish -> dish.id == dishId } ?: return

	val sendIntent: Intent = Intent().apply {
		action = Intent.ACTION_SEND
		putExtra(Intent.EXTRA_TEXT, dish.name + "\n" + dish.description)
		type = "text/plain"
	}
	val shareIntent = Intent.createChooser(sendIntent, null)
	val context = LocalContext.current

	FloatingActionButton(
		onClick = {
			context.startActivity(shareIntent)
		},
		containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
		elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
	) {
		Icon(Icons.Default.Save, "Save")
	}
}