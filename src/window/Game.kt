package window

import framework.GameArray
import framework.Node
import framework.ObjectID
import objects.PacMan
import objects.Pill
import java.awt.Canvas
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferStrategy
import java.util.*

class Game : Canvas(), Runnable{

    private var running = false

    lateinit var handler: ObjectHandler

    lateinit var bs: BufferStrategy
    lateinit var g: Graphics2D
    lateinit var gameArray: GameArray

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
                //println("FPS: $frames - Ticks: $updates")
                frames = 0
                updates = 0
            }
        }
    }

    fun initGame(){
        createBufferStrategy(2)
        bs = bufferStrategy
        handler = ObjectHandler()
        gameArray = createGameArray()

        val pacman = PacMan(1, 1, 32F, 32F, ObjectID.PACMAN, gameArray)

        handler.addGameObject(pacman)
    }

    //TODO - should this be moved to the game array class?
    fun createGameArray(): GameArray{
        val array = GameArray()
        val randomNumberGenerator = Random()
        var randomNum: Int
        var isPill: Boolean
        var newNode: Node

        //TODO - make array a property? Or is this method a little more readable/understandable?
        initialiseArray(array)
        createWalls(array)
        addPills()

        return array
    }

    fun initialiseArray(array: GameArray){

        var node: Node

        for(x in 0..GameArray.WIDTH-1){
            for(y in 0..GameArray.HEIGHT-1){
                node = Node(x, y, false)
                array.addNode(x, y, node)
            }
        }
    }

    fun createWalls(array: GameArray){
        val x = 13

        array.addNode(Node(x, 1, wall = true))
        array.addNode(Node(x, 2, wall = true))
        array.addNode(Node(x, 3, wall = true))
        array.addNode(Node(x, 4, wall = true))
        array.addNode(Node(x, 5, wall = true))
    }

    fun addPills(){

        val x = 16
        val y = 2

        handler.addGameObject(
            Pill(
                x,
                y,
                getPillCenterValue(x),
                getPillCenterValue(y),
                ObjectID.PILL
            )
        )
    }

    private fun getPillCenterValue(pos: Int): Float{
        //Position * tile size + half the sprite width/height - half sprite size
        return (pos*32+16-3).toFloat()
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






















