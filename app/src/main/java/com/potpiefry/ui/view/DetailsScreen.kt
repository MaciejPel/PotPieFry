package com.potpiefry.ui.view

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.potpiefry.R
import com.potpiefry.data.dishes
import com.potpiefry.ui.viewmodel.NavigationViewModel


@Composable
fun DetailsScreen(
	navigationViewModel: NavigationViewModel,
	dishId: Int,
) {
	var dish = dishes.find { dish -> dish.id == dishId }
	dish = dish ?: dishes[0]
	navigationViewModel.setDish(dish.id)

	val listState = rememberLazyListState()
	val overlapHeightPx = with(LocalDensity.current) {
		(200.dp).toPx() - (56.dp).toPx()
	}

	val isCollapsed: Boolean by remember {
		derivedStateOf {
			val isFirstItemHidden = listState.firstVisibleItemScrollOffset > overlapHeightPx
			isFirstItemHidden || listState.firstVisibleItemIndex > 0
		}
	}

	Box {
		CollapsedTopBar(modifier = Modifier.zIndex(2f), isCollapsed = isCollapsed, title = dish.name)
		LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
			item { ExpandedTopBar(dish.name, dish.img) }
			item {
				Column(
					modifier = Modifier
						.fillMaxSize()
						.padding(16.dp),
					horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.Center
				) {
					Card() {
						Column(modifier = Modifier.fillMaxWidth()) {
							Column(
								modifier = Modifier
									.padding(8.dp)
							) {
								Text(
									text = dish.description,
									fontStyle = MaterialTheme.typography.bodySmall.fontStyle
								)
							}
						}
					}
				}
			}
		}
	}
}

@Composable
fun ShareButton(dishId: Int?) {
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

@Composable
private fun ExpandedTopBar(title: String, img: String) {
	Box(
		modifier = Modifier
			.background(MaterialTheme.colorScheme.primaryContainer)
			.fillMaxWidth()
			.height(200.dp),
		contentAlignment = Alignment.BottomStart
	) {
		AsyncImage(
			modifier = Modifier
				.fillMaxSize(),
			model = ImageRequest.Builder(LocalContext.current)
				.data(img)
				.crossfade(true)
				.build(),
			placeholder = painterResource(R.drawable.placeholder),
			contentDescription = null,
			contentScale = ContentScale.Crop,
		)
		Text(
			modifier = Modifier.padding(16.dp),
			text = title,
			color = MaterialTheme.colorScheme.onPrimary,
			style = MaterialTheme.typography.headlineMedium,
		)
	}
}

@Composable
private fun CollapsedTopBar(modifier: Modifier = Modifier, isCollapsed: Boolean, title: String) {
	val color: Color by animateColorAsState(
		if (isCollapsed) MaterialTheme.colorScheme.background else Color.Transparent
	)
	Box(
		modifier = modifier
			.background(color)
			.fillMaxWidth()
			.height(56.dp)
			.padding(16.dp),
		contentAlignment = Alignment.BottomStart
	) {
		AnimatedVisibility(visible = isCollapsed) {
			Text(text = title, fontStyle = MaterialTheme.typography.headlineSmall.fontStyle)
		}
	}
}
