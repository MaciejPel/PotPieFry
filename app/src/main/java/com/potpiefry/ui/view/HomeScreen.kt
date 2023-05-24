package com.potpiefry.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.potpiefry.R
import com.potpiefry.data.DishPreview
import com.potpiefry.ui.viewmodel.HomeViewModel
import com.potpiefry.ui.viewmodel.TabType
import com.potpiefry.ui.viewmodel.homeViewTabs
import com.potpiefry.util.DeviceType
import com.potpiefry.util.getBodyTextSize
import com.potpiefry.util.getHeadlineTextSize
import com.potpiefry.util.getIconSize
import com.potpiefry.util.getTitleTextSize
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
	navController: NavController,
	homeViewModel: HomeViewModel = viewModel(),
	deviceType: DeviceType
) {
	val pagerState = rememberPagerState()
	val tabScope = rememberCoroutineScope()

	LaunchedEffect(Unit, block = {
		homeViewModel.getDishList()
	})

	Column(modifier = Modifier.fillMaxSize()) {
		if (homeViewModel.loading)
			LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
		if (!homeViewModel.loading && homeViewModel.errorMessage.isEmpty()) {
			val dishes =
				homeViewModel.dishList.filter {
					it.name.lowercase().contains(homeViewModel.query.lowercase())
				}
			TabRow(selectedTabIndex = pagerState.currentPage) {
				homeViewTabs.forEachIndexed { index, item ->
					Tab(
						selected = index == pagerState.currentPage,
						text = {
							Text(
								text = item.title,
								fontSize = getTitleTextSize(deviceType)
							)
						},
						onClick = {
							tabScope.launch { pagerState.animateScrollToPage(index) }
						},
					)
				}
			}
			HorizontalPager(pageCount = homeViewTabs.size, state = pagerState) { pageIndex ->
				when (homeViewTabs[pageIndex]) {
					TabType.Start -> {
						DishGrid(navController, dishes, deviceType)
					}

					TabType.Local -> {
						DishGrid(
							navController,
							dishes.filter { it.type == TabType.Local.value },
							deviceType
						)
					}

					TabType.Abroad -> {
						DishGrid(
							navController,
							dishes.filter { it.type == TabType.Abroad.value },
							deviceType
						)
					}
				}
			}
		}
		if (!homeViewModel.loading && homeViewModel.errorMessage.isNotEmpty()) {
			Column(
				modifier = Modifier
					.fillMaxSize()
					.padding(12.dp),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.Center,
			) {
				Text(
					"Error: " + homeViewModel.errorMessage,
					color = MaterialTheme.colorScheme.primary,
					fontSize = getTitleTextSize(deviceType),
					textAlign = TextAlign.Center,
					modifier = Modifier.padding(vertical = 12.dp)
				)
				ElevatedButton(onClick = {
					homeViewModel.getDishList()
				}) {
					Icon(Icons.Filled.Refresh, "Refresh", modifier = Modifier.size(getIconSize(deviceType)))
					Text("Try again", fontSize = getTitleTextSize(deviceType))
				}
			}
		}
	}
}


@Composable
fun DishGrid(
	navController: NavController,
	dishes: List<DishPreview>,
	deviceType: DeviceType
) {
	LazyVerticalGrid(
		modifier = Modifier.fillMaxSize(),
		columns = GridCells.Fixed(
			when (deviceType) {
				DeviceType.Tablet -> 2
				else -> 1
			}
		),
		verticalArrangement = Arrangement.spacedBy(12.dp),
		horizontalArrangement = Arrangement.spacedBy(12.dp),
		contentPadding = PaddingValues(12.dp)
	) {
		items(dishes) { dish ->
			Card(
				modifier = Modifier
					.clickable {
						navController.navigate(NavigationScreen.Details.passId(dish.id)) {
							popUpTo(navController.graph.findStartDestination().id) {
								saveState = true
							}
							launchSingleTop = true
							restoreState = true
						}
					},
			) {
				Row(
					modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically
				) {
					Box(
						modifier = Modifier.size(
							when (deviceType) {
								DeviceType.Tablet -> 120.dp
								else -> 80.dp
							}
						)
					) {
						AsyncImage(
							modifier = Modifier.aspectRatio(1f),
							model = ImageRequest.Builder(LocalContext.current)
								.data(dish.img)
								.crossfade(true)
								.build(),
							placeholder = painterResource(R.drawable.placeholder),
							contentDescription = null,
							contentScale = ContentScale.Crop,
						)
					}
					Column(
						modifier = Modifier
							.weight(1f)
							.padding(horizontal = 8.dp)
					) {
						Text(
							text = dish.name,
							fontSize = getHeadlineTextSize(deviceType),
							overflow = TextOverflow.Ellipsis,
							maxLines = 1,
						)
						Text(
							text = dish.description,
							fontSize = getTitleTextSize(deviceType),
							lineHeight = 20.sp,
							overflow = TextOverflow.Ellipsis,
							maxLines = when (deviceType) {
								DeviceType.Tablet -> 3
								else -> 2
							}
						)
					}
				}
			}
		}
	}
}