import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import java.lang.Math.pow
import kotlin.math.pow
import kotlin.math.sqrt

sealed class SceneEntity {
    abstract fun update(scene: Scene)
}

fun randomX(canvasWidth: Int) = (0..canvasWidth).random().toFloat()
fun randomValue(max: Int) = (1..max).random().toFloat()
fun randomFallSpeed(z: Float) = z.mapRange(0f to 20f, 4f to 10f)
fun randomY() = (-500..-50).random().toFloat()
fun randomZ() = (1..10).random().toFloat()
fun randomDropThickness(z: Float) = z.mapRange(0f to 20f, 1f to 5f)

data class Star(
    var canvasWidth: Float = Window.WIDTH_VALUE,
    var canvasHeight: Float = Window.HEIGHT_VALUE,
    var x: Float = randomX(canvasWidth.toInt()),
    var y: Float = randomY(),
    var z: Float = randomZ(),
    var length: Float = randomX(3),
    var thickness: Float = randomDropThickness(z),
) : SceneEntity() {
    var fallSpeed: Float = randomFallSpeed(z)
    val height get() = canvasHeight

    override fun update(scene: Scene) {
        y += fallSpeed
        if (y > height) {
            reset()
        }
    }

    private fun reset() {
        x = randomX(canvasWidth.toInt())
        y = 0f
        length = randomValue(3)
        fallSpeed = randomFallSpeed(z)
        thickness = randomDropThickness(z)
    }

}


fun DrawScope.drawStar(star: Star) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    star.canvasWidth = canvasWidth
    star.canvasHeight = canvasHeight
    drawLine(
        color = Color.White,
        start = Offset(star.x, star.y),
        end = Offset(star.x, star.y + star.length),
        strokeWidth = star.thickness,
    )
}

