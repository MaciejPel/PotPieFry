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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.potpiefry.R
import com.potpiefry.data.Dish
import com.potpiefry.data.TabType
import com.potpiefry.ui.viewmodel.HomeViewModel
import com.potpiefry.ui.viewmodel.NavigationViewModel
import com.potpiefry.ui.viewmodel.tabs
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
	navController: NavController,
	navigationViewModel: NavigationViewModel,
	homeViewModel: HomeViewModel = viewModel(),
) {
	navigationViewModel.setNavigation(NavigationScreen.Home.title, NavigationScreen.Home.route)
	val homeUiState by homeViewModel.uiState.collectAsState()
	val dishes =
		homeUiState.dishes.filter { it.name.lowercase().contains(homeUiState.query.lowercase()) }

	val pagerState = rememberPagerState()
	val tabScope = rememberCoroutineScope()

	Column(modifier = Modifier.fillMaxSize()) {
		TabRow(selectedTabIndex = pagerState.currentPage) {
			tabs.forEachIndexed { index, item ->
				Tab(
					selected = index == pagerState.currentPage,
					text = { Text(text = item.title) },
					onClick = {
						tabScope.launch { pagerState.animateScrollToPage(index) }
					},
				)
			}
		}
		HorizontalPager(pageCount = tabs.size, state = pagerState) { pageIndex ->
			when (tabs[pageIndex]) {
				TabType.Start -> {
					DishGrid(navController, dishes)
				}

				TabType.Local -> {
					DishGrid(navController, dishes.filter {
						it.type == TabType.Local
					})
				}

				TabType.Abroad -> {
					DishGrid(navController, dishes.filter {
						it.type == TabType.Abroad
					})
				}
			}
		}
	}

}

@Composable
fun DishGrid(navController: NavController, dishes: List<Dish>) {
	LazyVerticalGrid(
		modifier = Modifier.fillMaxSize(),
		columns = GridCells.Fixed(1),
		verticalArrangement = Arrangement.spacedBy(12.dp),
		horizontalArrangement = Arrangement.spacedBy(12.dp),
		contentPadding = PaddingValues(12.dp)
	) {
		items(
			dishes
		) { dish ->
			Card(
				modifier = Modifier
					.clickable {
						navController.navigate(route = NavigationScreen.Details.passId(dish.id))
					},
			) {
				Row(
					modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically
				) {
					Box(modifier = Modifier.size(80.dp)) {
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
							fontSize = MaterialTheme.typography.headlineSmall.fontSize,
							overflow = TextOverflow.Ellipsis,
							maxLines = 1,
						)
						Text(
							text = dish.description,
							fontSize = MaterialTheme.typography.bodyMedium.fontSize,
							lineHeight = 20.sp,
							overflow = TextOverflow.Ellipsis,
							maxLines = 2
						)
					}
				}
			}
		}
	}
}