package window

import framework.GameArray
import framework.GameObject
import framework.Node
import framework.ObjectID
import objects.PacMan
import objects.Pill
import java.awt.Canvas
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferStrategy
import java.util.*

/**
 * Created by michaelbrooke on 2016/04/23.
 */
class Game : Canvas(), Runnable{

    private var running = false

    val handler = ObjectHandler()

    lateinit var bs: BufferStrategy
    lateinit var g: Graphics2D

    fun startGame(){
        if(running)
            return

        running = true
        Thread(this).start()
    }

    override fun run() {
        println("Thread started")

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

    fun initGame(){
        createBufferStrategy(2)
        bs = bufferStrategy

        handler.addGameObject(PacMan(32F, 32F, ObjectID.PACMAN))
        handler.addGameObject(Pill(525F, 45F, ObjectID.PILL))
    }

    fun createGameArray(): GameArray{
        val array = GameArray()
        val randomNumberGenerator = Random()
        var randomNum = 0

        for(i in 1..GameArray.WIDTH){
            for(j in 1..GameArray.HEIGHT){

                randomNum = randomNumberGenerator.nextInt(100) + 1
                val isPill = if(randomNum >=30) true else false

                //TODO - was here last night
            }
        }

        return array
    }

    fun tick(){
        handler.tick()
    }

    fun render(){
        g = bs.drawGraphics as Graphics2D

        drawBackground(g)
        drawGrid(g)
        drawGameObjects(g)

        g.dispose()
        bs.show()
    }

    private fun drawBackground(g: Graphics2D){
        g.color = Color.black
        g.fillRect(0, 0, width, height)
    }

    private fun drawGrid(g: Graphics2D){
        g.color = Color.white

        var x = 32
        var y = 32

        while(x <= GAME_WIDTH){
            g.drawLine(x, 0, x, GAME_HEIGHT)
            x += 32
        }

        while(y <= GAME_HEIGHT){
            g.drawLine(0, y, GAME_WIDTH, y)
            y += 32
        }
    }

    private fun drawGameObjects(g: Graphics2D){
        handler.render(g)
    }

}






















