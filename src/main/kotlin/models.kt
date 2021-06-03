import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope

sealed class SceneEntity {
    abstract fun update(scene: Scene)
    abstract fun reset()
}

fun randomX(canvasWidth: Float) = (0..canvasWidth.toInt()).random().toFloat()
fun randomValue(max: Int) = (1..max).random().toFloat()
fun randomFallSpeed(z: Float) = z.mapRange(0f to 20f, 4f to 10f)
fun randomY() = (-500..-50).random().toFloat()
fun randomZ() = (1..10).random().toFloat()
fun randomLength(z: Float) = z.mapRange(0f to 20f, 10f to 25f)
fun randomThickness(z: Float) = z.mapRange(0f to 20f, 3f to 5f)

data class Rocket(
    var coordinates: Triple<Float, Float, Float> = Triple(0f, 0f, 0f),
    var velocity: Triple<Float, Float, Float> = Triple(0f, -10f, 0f),
    var acceleartion: Triple<Float, Float, Float> = Triple(0f, 0f, 0f),
) : SceneEntity() {


    val length: Float get() = randomLength(coordinates.third)
    val thickness: Float get() = randomThickness(coordinates.third)

    var canvasHeight: Float = 0f
    var canvasWidth: Float = 0f

    fun applyForce(force: Triple<Float, Float, Float>) {
        acceleartion += force
    }

    override fun update(scene: Scene) {
        velocity += acceleartion
        coordinates += velocity
        acceleartion *= 0.0f
        if (coordinates.second > canvasHeight) {
            reset()
        }
    }

    override fun reset() {
        coordinates = Triple(randomX(canvasWidth), canvasHeight, 0f)
        velocity = Triple(0f, -1 * (8..15).random().toFloat(), 0f)
        acceleartion = Triple(0f, 0f, 0f)
    }

}

fun DrawScope.drawRocket(rocket: Rocket) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    rocket.canvasWidth = canvasWidth
    rocket.canvasHeight = canvasHeight
    val (x, y, z) = rocket.coordinates
    drawCircle(
        color = Color.White,
        radius = 20f,
        center = Offset(x, y)
    )
}


