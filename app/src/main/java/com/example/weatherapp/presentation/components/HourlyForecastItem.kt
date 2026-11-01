package com.example.weatherapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weatherapp.domain.model.HourlyForecast
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun HourlyForecastItem(
    forecast: HourlyForecast,
    modifier: Modifier = Modifier
) {

    val time = forecast.time
        .substringAfter("T", "")
        .take(5)
        .ifBlank { "--:--" }

    val temperature = forecast.temperature.roundToInt()

    val temperatureText = if (temperature > 0) {
        "+$temperature°"
    } else {
        "$temperature°"
    }

    val weatherIcon = getWeatherIcon(
        forecast.weatherCode
    )

    val weatherDescription = getWeatherDescription(
        forecast.weatherCode
    )

    Card(
        modifier = modifier.width(105.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {

        Column(
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 16.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = time,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = weatherIcon,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = temperatureText,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "💧 ${forecast.precipitationProbability}%",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

/**
 * Возвращает значок погодных условий
 * на основании кода погоды Open-Meteo.
 */
private fun getWeatherIcon(
    weatherCode: Int
): String {

    return when (weatherCode) {

        0 -> "☀️"

        1, 2 -> "🌤️"

        3 -> "☁️"

        45, 48 -> "🌫️"

        51, 53, 55,
        56, 57 -> "🌦️"

        61, 63, 65,
        66, 67 -> "🌧️"

        71, 73, 75, 77 -> "❄️"

        80, 81, 82 -> "🌧️"

        85, 86 -> "🌨️"

        95, 96, 99 -> "⛈️"

        else -> "🌡️"
    }
}

/**
 * Возвращает текстовое описание
 * погодных условий.
 */
private fun getWeatherDescription(
    weatherCode: Int
): String {

    return when (weatherCode) {

        0 -> "Ясно"

        1 -> "Преимущественно ясно"

        2 -> "Переменная облачность"

        3 -> "Пасмурно"

        45, 48 -> "Туман"

        51, 53, 55 -> "Морось"

        56, 57 -> "Ледяная морось"

        61, 63, 65 -> "Дождь"

        66, 67 -> "Ледяной дождь"

        71, 73, 75 -> "Снег"

        77 -> "Снежные зёрна"

        80, 81, 82 -> "Ливень"

        85, 86 -> "Снегопад"

        95 -> "Гроза"

        96, 99 -> "Гроза с градом"

        else -> "Неизвестно"
    }
}