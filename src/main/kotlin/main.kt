import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    private val rockets = mutableListOf<Fruit>()
    val particles = mutableMapOf<Int, List<Particle>>()
    private val gravity = Triple(0f, 0.2f, 0f)

    fun setupScene() {
        sceneEntity.clear()
        repeat(5) { id ->
            val rocket = Fruit(id = id, coordinates = Triple(Window.WIDTH_VALUE, Window.HEIGHT_VALUE, 10f))
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
        Box(modifier = Modifier.fillMaxSize()) {

            var mousePosition by remember { mutableStateOf(0f to 0f) }
            Surface(color = Color.White) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Black)
                        .pointerMoveFilter(onMove = {
                            val (x, y) = it
                            mousePosition = mousePosition.copy(x, y)
                            true
                        }),
                ) {
                    val stepFrame = frameState.value

                    for ((index, rocket) in rockets.withIndex()) {

                        rocket.applyForce(gravity)
                        drawFruit(rocket)

                        val rocketParticle = particles.get(index)
                        rocketParticle?.forEach {
                            it.applyForce(gravity)
                            drawParticles(it)
                        }

                        drawPlayer(mousePosition, rockets) {
                            it.explode(this@Scene)
                        }
                    }
                }
            }

            Text(
                text = "Chetan Ninja!",
                modifier = Modifier
                    .offset(y = 55.dp)
                    .fillMaxWidth()
                    .height(200.dp),
                textAlign = TextAlign.Center,
                fontSize = 85.sp,
                color = Color.White,
                style = MaterialTheme.typography.h1
            )
        }

    }
}




