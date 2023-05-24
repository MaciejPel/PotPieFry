package com.potpiefry.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

fun getIconSize(deviceType: DeviceType): Dp {
	return when (deviceType) {
		DeviceType.Tablet -> 36.dp
		else -> 24.dp
	}
}

@Composable
fun getBodyTextSize(deviceType: DeviceType): TextUnit {
	return when (deviceType) {
		DeviceType.Tablet -> MaterialTheme.typography.bodyLarge.fontSize
		else -> MaterialTheme.typography.bodyMedium.fontSize
	}
}

@Composable
fun getBodyTextStyle(deviceType: DeviceType): TextStyle {
	return when (deviceType) {
		DeviceType.Tablet -> MaterialTheme.typography.bodyLarge
		else -> MaterialTheme.typography.bodyMedium
	}
}

@Composable
fun getLabelTextSize(deviceType: DeviceType): TextUnit {
	return when (deviceType) {
		DeviceType.Tablet -> MaterialTheme.typography.labelLarge.fontSize
		else -> MaterialTheme.typography.labelMedium.fontSize
	}
}

@Composable
fun getLabelTextStyle(deviceType: DeviceType): TextStyle {
	return when (deviceType) {
		DeviceType.Tablet -> MaterialTheme.typography.labelLarge
		else -> MaterialTheme.typography.labelMedium
	}
}

@Composable
fun getTitleTextSize(deviceType: DeviceType): TextUnit {
	return when (deviceType) {
		DeviceType.Tablet -> MaterialTheme.typography.titleLarge.fontSize
		else -> MaterialTheme.typography.titleMedium.fontSize
	}
}

@Composable
fun getTitleTextStyle(deviceType: DeviceType): TextStyle {
	return when (deviceType) {
		DeviceType.Tablet -> MaterialTheme.typography.titleLarge
		else -> MaterialTheme.typography.titleMedium
	}
}

@Composable
fun getHeadlineTextSize(deviceType: DeviceType): TextUnit {
	return when (deviceType) {
		DeviceType.Tablet -> MaterialTheme.typography.headlineLarge.fontSize
		else -> MaterialTheme.typography.headlineMedium.fontSize
	}
}

@Composable
fun getHeadlineTextStyle(deviceType: DeviceType): TextStyle {
	return when (deviceType) {
		DeviceType.Tablet -> MaterialTheme.typography.headlineLarge
		else -> MaterialTheme.typography.headlineMedium
	}
}

@Composable
fun getDisplayTextSize(deviceType: DeviceType): TextUnit {
	return when (deviceType) {
		DeviceType.Tablet -> MaterialTheme.typography.displayLarge.fontSize
		else -> MaterialTheme.typography.displayMedium.fontSize
	}
}

@Composable
fun getDisplayTextStyle(deviceType: DeviceType): TextStyle {
	return when (deviceType) {
		DeviceType.Tablet -> MaterialTheme.typography.displayLarge
		else -> MaterialTheme.typography.displayMedium
	}
}

@Composable
fun StyledText(text: String, style: TextStyle, bold: Boolean? = false) {
	return Text(
		text = text,
		fontSize = style.fontSize,
		fontWeight = if (bold == true) FontWeight.Bold else style.fontWeight,
		fontStyle = style.fontStyle,
		lineHeight = style.lineHeight
	)
}