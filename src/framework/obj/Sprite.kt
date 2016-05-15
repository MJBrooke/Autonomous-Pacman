package framework.obj

import framework.search.DIRECTION
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import java.util.*

const val SIZE = 32

abstract class Sprite(arrayX: Int, arrayY: Int, val array: GameArray) : GameObject(arrayX, arrayY){

    var left = false
    var right = false
    var up = false
    var down = false

    var prevDir = DIRECTION.LEFT

    var velocity = 4F

    lateinit var path: LinkedList<Node>
    lateinit var nextSquareToMoveTo: Node

    init{
        width = SIZE
        height = SIZE
    }

    override fun tick() {
        updateGridPosition()
        updatePath()
        updateDirectionOfMovement()
        move()
    }

    override fun render(g: Graphics2D){
        affineTransform = AffineTransform()
        affineTransform.translate(x.toDouble(), y.toDouble())
        affineTransform.scale(1.0, 1.0)
    }

    private fun updateGridPosition(){
        if(x % SIZE == 0F)
            arrayX = (x / SIZE).toInt()

        if(y % SIZE == 0F)
            arrayY = (y / SIZE).toInt()

    }

    protected abstract fun updatePath()

    private fun updateDirectionOfMovement(){

        getNextSquareToMoveTo()
        resetDirections()
        setDirectionToMove()
    }

    protected open fun getNextSquareToMoveTo(){
        if(isAtNextSquare()) {
            if(!path.isEmpty())
                nextSquareToMoveTo = path.remove()
        }
    }

    private fun resetDirections(){
        right = false
        left = false
        down = false
        up = false
    }

    private fun setDirectionToMove(){
        when {
            movingRight() -> {
                prevDir = DIRECTION.RIGHT
                right = true
            }
            movingLeft() -> {
                prevDir = DIRECTION.LEFT
                left = true
            }
            movingDown() -> {
                prevDir = DIRECTION.DOWN
                down = true
            }
            movingUp() -> {
                prevDir = DIRECTION.UP
                up = true
            }
        }
    }

    private fun movingRight() = nextSquareToMoveTo.x > arrayX

    private fun movingLeft() = nextSquareToMoveTo.x < arrayX

    private fun movingDown() = nextSquareToMoveTo.y > arrayY

    private fun movingUp() = nextSquareToMoveTo.y < arrayY

    private fun isAtNextSquare() = arrayX == nextSquareToMoveTo.x && arrayY == nextSquareToMoveTo.y

    private fun move(){
        when{
            right -> x += velocity
            left -> x -= velocity
            up -> y -= velocity
            down -> y += velocity
        }
    }

    fun getCurrentLocation() = Tuple(arrayX, arrayY)
}