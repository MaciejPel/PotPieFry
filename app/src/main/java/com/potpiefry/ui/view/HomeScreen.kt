package com.potpiefry.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.potpiefry.data.dishes

@Composable
fun HomeScreen() {
	Column(modifier = Modifier) {
		LazyVerticalGrid(
			columns = GridCells.Fixed(2),
			verticalArrangement = Arrangement.spacedBy(12.dp),
			horizontalArrangement = Arrangement.spacedBy(12.dp),
			contentPadding = PaddingValues(16.dp)
		) {
			items(dishes) { dish ->
				Card(
					modifier = Modifier
						.clickable { println(dish) },
				) {
					Row(
						modifier = Modifier
							.fillMaxSize()
							.padding(16.dp), verticalAlignment = Alignment.CenterVertically
					) {
						Column(modifier = Modifier.weight(1f)) {
							Text(
								text = dish.name,
								fontSize = MaterialTheme.typography.headlineSmall.fontSize,
								maxLines = 1,
								overflow = TextOverflow.Ellipsis,
							)
							Text(
								text = dish.description,
								fontSize = MaterialTheme.typography.bodyMedium.fontSize,
								lineHeight = 20.sp,
								overflow = TextOverflow.Ellipsis,
								maxLines = 4
							)
						}
						Icon(
							imageVector = Icons.Default.KeyboardArrowRight,
							contentDescription = "Navigation Icon", modifier = Modifier.padding(4.dp)
						)
					}
				}
			}
		}
	}
}