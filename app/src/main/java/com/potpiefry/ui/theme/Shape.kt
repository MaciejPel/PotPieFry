package com.potpiefry.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp

data class Shape(
	val leftZeroRightFull: RoundedCornerShape = RoundedCornerShape(0.dp, 64.dp, 64.dp, 0.dp),
	val none: RoundedCornerShape = RoundedCornerShape(0.dp),
)

val LocalShape = compositionLocalOf { Shape() }

val MaterialTheme.shapeScheme: Shape
	@Composable
	@ReadOnlyComposable
	get() = LocalShape.current