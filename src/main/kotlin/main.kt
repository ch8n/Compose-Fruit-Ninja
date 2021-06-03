import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

fun main() {

    Preview {
        val scene = remember { Scene() }
        scene.setupScene()
        val frameState = StepFrame {
            scene.update()
        }
        scene.render(frameState)
    }
}


class Scene {


    var sceneEntity = mutableStateListOf<SceneEntity>()
    private val rockets = mutableListOf<Rocket>()
    val particles = mutableMapOf<Int, List<Particle>>()
    private val gravity = Triple(0f, 0.2f, 0f)

    fun setupScene() {
        sceneEntity.clear()
        repeat(10) { id ->
            val rocket = Rocket(id = id, coordinates = Triple(Window.WIDTH_VALUE, Window.HEIGHT_VALUE, 10f))
            rockets.add(rocket)
            val rocketParticle = mutableListOf<Particle>()
            repeat((25..100).random()) {
                val particle = Particle()
                rocketParticle.add(particle)
            }
            particles.put(id, rocketParticle)
        }

        sceneEntity.addAll(rockets)
        particles.values.forEach { rocketParticles ->
            sceneEntity.addAll(rocketParticles)
        }

    }

    fun update() {
        for (entity in sceneEntity) {
            entity.update(this)
        }
    }


    @Composable
    fun render(frameState: State<Long>) {

        Surface(color = Color.White) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black)
            ) {
                val stepFrame = frameState.value

                for ((index, rocket) in rockets.withIndex()) {
                    rocket.applyForce(gravity)
                    drawRocket(rocket)
                    val rocketParticle = particles.get(index)
                    rocketParticle?.forEach {
                        it.applyForce(gravity)
                        drawParticles(it)
                    }
                }
            }
        }
    }
}



