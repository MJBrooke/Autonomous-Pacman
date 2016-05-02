package objects

import framework.obj.*
import framework.search.AStarSearch
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.geom.AffineTransform
import java.io.File
import java.util.*
import javax.imageio.ImageIO

class PacMan(arrayX: Int, arrayY: Int, id: ObjectID, val array: GameArray) : GameObject(arrayX, arrayY, id){

    val rectangle = Rectangle(width, height)
    val image = ImageIO.read(File("res/Pacman.png"))

    //Setup the initial path of pacman
    var path = AStarSearch.findAPath(array, Tuple(arrayX, arrayY), Tuple(15, 23))
    var nextSquareToMoveTo = path.remove()

    init{
        width = 32
        height = 32
    }

    override fun tick(objects: LinkedList<GameObject>) {
        updateGridPosition()
        updatePath()
        updateDirectionOfMovement()
        move()
        checkCollision()
    }

    private fun updateGridPosition(){
        if(x % 32 == 0F) //Ensures that pacman's position is only updated when his top left hand corner is fully in the next block
            arrayX = (x/32).toInt()

        if(y % 32 == 0F)
            arrayY = (y/32).toInt()
    }

    fun updatePath(){
        if(path.isEmpty()){

            val closestPill = array.getClosestPillLocation(arrayX, arrayY)

            if(isAnotherPill(closestPill)) {
                path = AStarSearch.findAPath(array, Tuple(arrayX, arrayY), closestPill)
            }
        }
    }

    private fun isAnotherPill(pill: Tuple) = pill.first != 0 && pill.second != 0

    private fun checkCollision(){

        val node = array.getNode(arrayX, arrayY)

        if(node.pill != null){
            node.pill = null
        }
    }

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
            nextSquareToMoveTo.x > arrayX -> right = true
            nextSquareToMoveTo.x < arrayX -> left = true
            nextSquareToMoveTo.y > arrayY -> down = true
            nextSquareToMoveTo.y < arrayY -> up = true
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

    override fun render(g: Graphics2D) {
        affineTransform = AffineTransform()
        affineTransform.translate(x.toDouble(), y.toDouble())
        affineTransform.scale(1.0, 1.0)
        g.drawImage(image, affineTransform, null)

        //TODO - remove this rendering after debugging is done
        //Render path
        g.color = Color.GREEN
        path.forEach{
            g.drawRect(it.x*32, it.y*32, 24, 24)
        }
    }

    override fun getBounds(): Rectangle {
        rectangle.x = x.toInt()
        rectangle.y = y.toInt()
        return rectangle
    }
}























