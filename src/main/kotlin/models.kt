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
fun randomColor() = listOf<Color>(Color.Magenta, Color.Cyan, Color.Green, Color.Red, Color.Yellow).random()

data class Rocket(
    var coordinates: Triple<Float, Float, Float> = Triple(0f, 0f, 0f),
    var velocity: Triple<Float, Float, Float> = Triple(0f, -1 * (8..15).random().toFloat(), 0f),
    var acceleartion: Triple<Float, Float, Float> = Triple(0f, 0f, 0f),
    var color: Color = randomColor()
) : SceneEntity() {

    var canvasHeight: Float = 0f
    var canvasWidth: Float = 0f

    fun applyForce(force: Triple<Float, Float, Float>) {
        acceleartion += force
    }

    override fun update(scene: Scene) {
        velocity += acceleartion
        coordinates += velocity
        acceleartion *= 0.0f
        val (x, y, z) = coordinates
        if (velocity.second > 0f) {
            explode(scene)
            reset()
        }
    }

    fun explode(scene: Scene) {
        scene.particles.forEach { particle ->
            particle.color = color
            particle.coordinates = coordinates.copy(
                first = coordinates.first,
                second = coordinates.second
            )
        }
    }

    override fun reset() {
        coordinates = Triple(randomX(canvasWidth), canvasHeight, 0f)
        velocity = Triple(0f, -1 * (8..15).random().toFloat(), 0f)
        acceleartion = Triple(0f, 0f, 0f)
        color = randomColor()
    }

}


fun DrawScope.drawRocket(rocket: Rocket) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    rocket.canvasWidth = canvasWidth
    rocket.canvasHeight = canvasHeight
    val (x, y, _) = rocket.coordinates
    drawCircle(
        color = rocket.color,
        radius = 8f,
        center = Offset(x, y)
    )
}


data class Particle(
    var coordinates: Triple<Float, Float, Float> = Triple(0f, 0f, 0f),
    var velocity: Triple<Float, Float, Float> = Triple((-5..2).random().toFloat(), (-3..2).random().toFloat(), 0f),
    var acceleartion: Triple<Float, Float, Float> = Triple(2f, 2f, 0f),
    var color: Color = randomColor()
) : SceneEntity() {

    var canvasHeight: Float = 0f
    var canvasWidth: Float = 0f

    fun applyForce(force: Triple<Float, Float, Float>) {
        acceleartion += force
    }

    override fun update(scene: Scene) {
        velocity += acceleartion
        coordinates += velocity
        acceleartion *= 0.0f
    }

    override fun reset() {

    }

}


fun DrawScope.drawParticles(particle: Particle) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    particle.canvasWidth = canvasWidth
    particle.canvasHeight = canvasHeight
    val (x, y, _) = particle.coordinates
    drawCircle(
        color = particle.color,
        radius = 3f,
        center = Offset(x, y)
    )
}






