package window

import objects.GameWorld
import java.awt.Canvas
import java.awt.Color
import java.awt.Graphics2D

class Game : Canvas(), Runnable{

    val gameWorld = GameWorld()
    val bs by lazy { bufferStrategy }

    private var running = false

    lateinit var g: Graphics2D

    fun startGame(){
        if(running)
            return

        running = true
        Thread(this).start()
    }

    override fun run() {
        println("Game started")

        initGame()

        requestFocus()

        var lastTime = System.nanoTime()
        val amountOfTicks = 60.0
        val ns = 1000000000 / amountOfTicks
        var delta = 0.0
        var timer = System.currentTimeMillis()
        var updates = 0
        var frames = 0

        while (running) {
            val now = System.nanoTime()
            delta += (now - lastTime) / ns
            lastTime = now
            while (delta >= 1) {
                tick()
                updates++
                delta--
            }
            render() //Renders as fast as the computer can go, but updates game at a consistent rate of 60
            frames++

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000
                println("FPS: $frames - Ticks: $updates")
                frames = 0
                updates = 0
            }
        }
    }

    private fun initGame(){
        createBufferStrategy(2)
        loadLevel()
    }

    private fun loadLevel(){
        gameWorld.loadLevelFromImage("res/PacManLevel.png")
    }

    private fun tick(){
        gameWorld.tick()
    }

    private fun render(){
        g = bs.drawGraphics as Graphics2D

        renderGame(g)

        g.dispose()
        bs.show()
    }

    private fun renderGame(g: Graphics2D){
        renderBackground(g)
        renderWorld(g)
    }

    private fun renderBackground(g: Graphics2D){
        g.color = Color.black
        g.fillRect(0, 0, width, height)
    }

    private fun renderWorld(g: Graphics2D){
        gameWorld.render(g)
    }
}






















