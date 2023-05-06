package com.potpiefry.ui.view

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.potpiefry.R
import com.potpiefry.data.Dish
import com.potpiefry.ui.viewmodel.DetailsViewModel
import com.potpiefry.ui.viewmodel.NavigationViewModel


@Composable
fun DetailsScreen(
    navigationViewModel: NavigationViewModel,
    detailsViewModel: DetailsViewModel,
    dishId: Int
) {
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

    LaunchedEffect(Unit, block = {
        detailsViewModel.getDish(dishId)
    })

    navigationViewModel.setNavigation(
        detailsViewModel.dish?.name ?: "",
        NavigationScreen.Details.route
    )

    Box {
        if (detailsViewModel.errorMessage.isEmpty() && detailsViewModel.dish != null) {
            val dish = detailsViewModel.dish!!
            CollapsedTopBar(
                modifier = Modifier.zIndex(2f),
                isCollapsed = isCollapsed,
                title = dish.name
            )
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
                        Card {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(
                                        text = "Description",
                                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                        fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
                                        fontStyle = MaterialTheme.typography.labelLarge.fontStyle,
                                        lineHeight = MaterialTheme.typography.labelLarge.lineHeight

                                    )
                                    Text(
                                        text = dish.description,
                                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                        fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                                        fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
                                    )
                                }
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(
                                        text = "Ingredients",
                                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                        fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
                                        fontStyle = MaterialTheme.typography.labelLarge.fontStyle,
                                        lineHeight = MaterialTheme.typography.labelLarge.lineHeight
                                    )
                                    Column {
                                        dish.ingredients.forEach { ing ->
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Box(
                                                    modifier = Modifier
														.padding(4.dp)
														.size(4.dp)
														.background(
															MaterialTheme.colorScheme.onSurfaceVariant,
															shape = CircleShape
														),
                                                )
                                                Text(
                                                    text = "${ing.amount}${if (ing.unit.isNotEmpty()) " ${ing.unit}" else ""} of ${ing.ingredient}",
                                                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                                    fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                                                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                                                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                                                )
                                            }
                                        }
                                    }
                                }
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(
                                        text = "Steps",
                                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                        fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
                                        fontStyle = MaterialTheme.typography.labelLarge.fontStyle,
                                        lineHeight = MaterialTheme.typography.labelLarge.lineHeight

                                    )
                                    Column {
                                        dish.steps.forEachIndexed { index, step ->
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                Arrangement.spacedBy(8.dp),
                                                Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = "${index + 1}. ${step.description}",
                                                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                                    fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                                                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                                                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                                                )
//												if (step.duration != null && step.durationUnit != null)
//													ElevatedButton(onClick = { /*TODO*/ }) {
//														Icon(Icons.Filled.Timer, "Timer")
//														Text(text = "${step.duration}${step.durationUnit}")
//													}
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Text(detailsViewModel.errorMessage)
        }
    }
}

@Composable
fun ShareButton(dish: Dish) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            dish.name + "\n" + dish.ingredients.joinToString(
                prefix = "- ",
                separator = ",\n- ",
                postfix = "\nSmacznego 🥰"
            ) { ing -> "${ing.amount}${if (ing.unit.isNotEmpty()) " ${ing.unit}" else ""} of ${ing.ingredient}" })
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
private fun ExpandedTopBar(title: String, img: String?) {
    var sizeImage by remember {
        mutableStateOf(IntSize.Zero)
    }
    val gradient = Brush.verticalGradient(
        colors = listOf(Color.Transparent, Color.Black),
        startY = sizeImage.height.toFloat() / 3,
        endY = sizeImage.height.toFloat()
    )

    Box(
        modifier = Modifier
			.background(MaterialTheme.colorScheme.primaryContainer)
			.fillMaxWidth()
			.height(200.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        if (img == null) {
            Image(
                painterResource(R.drawable.placeholder),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        } else {
            AsyncImage(
                modifier = Modifier
					.fillMaxSize()
					.onGloballyPositioned {
						sizeImage = it.size
					},
                model = ImageRequest.Builder(LocalContext.current)
                    .data(img)

                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Box(modifier = Modifier
				.matchParentSize()
				.background(gradient))
        }
        Text(
            modifier = Modifier.padding(16.dp),
            text = title,
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            fontWeight = MaterialTheme.typography.headlineLarge.fontWeight,
            fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
            color = MaterialTheme.colorScheme.primary,
//			style = TextStyle(
//				shadow = Shadow(
//					color = MaterialTheme.colorScheme.tertiaryContainer,
//					offset = Offset(1f, 1f),
//					blurRadius = 8f
//				)
//			)
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
			.height(64.dp)
			.padding(16.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        AnimatedVisibility(visible = isCollapsed) {
            Text(
                text = title,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                fontWeight = MaterialTheme.typography.headlineSmall.fontWeight,
                fontStyle = MaterialTheme.typography.headlineSmall.fontStyle
            )
        }
    }
}
