import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

sealed class SceneEntity {
    abstract fun update(scene: Scene)
    abstract fun reset()
}

fun randomX(canvasWidth: Float) = (0..canvasWidth.toInt()).random().toFloat()
fun randomColor() = listOf<Color>(Color.Magenta, Color.Cyan, Color.Green, Color.Red, Color.Yellow).random()

data class Rocket(
    var id: Int,
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
        if (velocity.second > 0f) {
            explode(scene)
            reset()
        }
    }

    fun explode(scene: Scene) {
        scene.particles.forEach { (id, rocketParticles) ->
            if (this.id == id) {
                rocketParticles.forEach { particle ->
                    particle.isExplosionReset = true
                    particle.color = color
                    particle.coordinates = coordinates.copy(
                        first = coordinates.first + (-100..100).random(),
                        second = coordinates.second + (-100..100).random(),
                    )
                }
            }
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
    var velocity: Triple<Float, Float, Float> = Triple(0f, -8f, 0f),
    var acceleartion: Triple<Float, Float, Float> = Triple(0f, 0f, 0f),
    var color: Color = randomColor()
) : SceneEntity() {

    var isExplosionReset = false
    var canvasHeight: Float = 0f
    var canvasWidth: Float = 0f

    fun applyForce(force: Triple<Float, Float, Float>) {
        acceleartion += force
    }

    override fun update(scene: Scene) {
        velocity += acceleartion
        coordinates += velocity
        acceleartion *= 0.0f
        if (isExplosionReset) {
            reset()
        }
    }

    override fun reset() {
        velocity = Triple(0f, 0f, 0f)
        acceleartion = Triple(0f, 0f, 0f)
        isExplosionReset = false
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






