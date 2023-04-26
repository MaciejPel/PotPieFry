package com.potpiefry.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
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


@Composable
fun DetailsScreen(
	dishId: Int,
	navController: NavController,
) {
	val dish = dishes.find { dish -> dish.id == dishId }

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		Card() {
			Column(modifier = Modifier.fillMaxWidth()) {
				if (dish != null) {
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
				} else {
					navController.navigate("/home")
				}
			}
		}
	}
}