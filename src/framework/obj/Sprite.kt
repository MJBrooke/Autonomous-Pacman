package framework.obj

import framework.search.DIRECTION
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.util.*

abstract class Sprite(arrayX: Int, arrayY: Int, id: ObjectID, val array: GameArray) : GameObject(arrayX, arrayY, id){

    var left = false
    var right = false
    var up = false
    var down = false

    var prevDir = DIRECTION.LEFT

    var velocity = 4F

    lateinit var path: LinkedList<Node>
    lateinit var nextSquareToMoveTo: Node
    lateinit var image: BufferedImage

    init{
        width = 32
        height = 32
    }

    override fun tick(objects: LinkedList<GameObject>) {
        updateGridPosition()
        updatePath(objects)
        updateDirectionOfMovement()
        move()
    }

    protected fun updateGridPosition(){
        if(x % 32F == 0F)
            arrayX = (x / 32).toInt()

        if(y % 32F == 0F)
            arrayY = (y / 32).toInt()

    }

    abstract fun updatePath(objects: LinkedList<GameObject>)

    fun getCurrentLocation() = Tuple(arrayX, arrayY)

    fun updateDirectionOfMovement(){

        if(isAtNextSquare()) {
            if(!path.isEmpty())
                nextSquareToMoveTo = path.remove()
        }

        right = false
        left = false
        down = false
        up = false

        when {
            nextSquareToMoveTo.x > arrayX -> {
                prevDir = DIRECTION.RIGHT
                right = true
            }
            nextSquareToMoveTo.x < arrayX -> {
                prevDir = DIRECTION.LEFT
                left = true
            }
            nextSquareToMoveTo.y > arrayY -> {
                prevDir = DIRECTION.DOWN
                down = true
            }
            nextSquareToMoveTo.y < arrayY -> {
                prevDir = DIRECTION.UP
                up = true
            }
        }
    }

    fun isAtNextSquare() = arrayX == nextSquareToMoveTo.x && arrayY == nextSquareToMoveTo.y

    fun move(){
        when{
            right -> x += velocity
            left -> x -= velocity
            up -> y -= velocity
            down -> y += velocity
        }
    }

    override fun render(g: Graphics2D){
        affineTransform = AffineTransform()
        affineTransform.translate(x.toDouble(), y.toDouble())
        affineTransform.scale(1.0, 1.0)
        g.drawImage(image, affineTransform, null)
    }
}