import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Window
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

/**
 * To support instant preview (replacement for android's @Preview annotation)
 */
fun Preview(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    application {
        Window(
            title = "Compose-fruit-ninja",
            resizable = false,
            state = rememberWindowState(size = DpSize(800.dp, 600.dp)),
            onCloseRequest = {
                this.exitApplication()
            },
        ) {
            MaterialTheme(typography = Typography) {
                Surface(modifier = modifier.fillMaxSize()) {
                    content()
                }
            }
        }
    }
}

object Window {
    val DEBUG = true
    val WIDTH = if (DEBUG) 800 else 1200
    val HEIGHT = if (DEBUG) 400 else 800
    val WIDTH_VALUE = WIDTH.dp.value
    val HEIGHT_VALUE = HEIGHT.dp.value
}

val backgroundColor = Color(0xffe6e6fa)
val shipColor = Color(0xff8a2be2)


fun Float.mapRange(fromRange: Pair<Float, Float>, toRange: Pair<Float, Float>): Float {
    val (minRange, maxRange) = fromRange
    val (minMappedRange, maxMappedRange) = toRange
    val rangePercentage = (this / maxRange) * 100
    val mappedValue = (rangePercentage / 100) * (minMappedRange + maxMappedRange)
    return mappedValue
}

operator fun Triple<Float, Float, Float>.times(that: Float): Triple<Float, Float, Float> {
    return Triple(this.first * that, this.second * that, this.third * that)
}

operator fun Triple<Float, Float, Float>.plus(that: Triple<Float, Float, Float>): Triple<Float, Float, Float> {
    return Triple(this.first + that.first, this.second + that.second, this.third + that.third)
}

operator fun Triple<Float, Float, Float>.plus(that: Float): Triple<Float, Float, Float> {
    return Triple(this.first + that, this.second + that, this.third + that)
}

operator fun Triple<Float, Float, Float>.div(that: Float): Triple<Float, Float, Float> {
    return this.copy(first / that, second / that, third / that)
}


val FruitNinja = FontFamily(
    Font(resource = "fonts/ninja.ttf", weight = FontWeight.Normal, style = FontStyle.Normal),
)

val Typography = Typography(
    h1 = TextStyle(
        fontFamily = FruitNinja,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        color = Color.White
    )
)





