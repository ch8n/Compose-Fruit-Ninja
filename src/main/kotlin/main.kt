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
    val particles = mutableStateListOf<Particle>()
    private val gravity = Triple(0f, 0.2f, 0f)

    fun setupScene() {
        sceneEntity.clear()
        repeat(1) {
            val rocket = Rocket(coordinates = Triple(Window.WIDTH_VALUE, Window.HEIGHT_VALUE, 10f))
            rockets.add(rocket)
        }
        repeat(50) {
            val particle = Particle()
            particles.add(particle)
        }
        sceneEntity.addAll(rockets)
        sceneEntity.addAll(particles)
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

                for (rocket in rockets) {
                    rocket.applyForce(gravity)
                    drawRocket(rocket)
                }

                for (particle in particles) {
                    drawParticles(particle)
                }

            }
        }
    }
}



