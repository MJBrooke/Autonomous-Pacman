package objects

import framework.extension.render
import framework.obj.*
import framework.search.AStarSearch
import framework.search.ESCAPE
import java.awt.Graphics2D
import java.io.File
import java.util.*
import javax.imageio.ImageIO

class PacMan(arrayX: Int, arrayY: Int, array: GameArray) : Sprite(arrayX, arrayY, ObjectID.PACMAN, array){

    init {
        image = ImageIO.read(File("res/Pacman.png"))
        path = AStarSearch.findAPath(array, Tuple(arrayX, arrayY), Tuple(15, 23))
        nextSquareToMoveTo = path.remove()
    }

    override fun tick(objects: LinkedList<GameObject>) {
        super.tick(objects)
        checkCollision()
    }

    override fun updatePath(objects: LinkedList<GameObject>){

        //case: Ghost is nearby - move in opposite direction

        var ghostNearby = false
        var ghostX = 0
        var ghostY = 0
        objects.forEach {
            if (it is Ghost) {
                if (it.arrayX >= arrayX - 5 && it.arrayX <= arrayX + 5) {
                    if (it.arrayY >= arrayY - 5 && it.arrayY <= arrayY + 5) {
                        ghostNearby = true
                        ghostX = it.arrayX
                        ghostY = it.arrayY
                    }
                }
            }
        }

        if (ghostNearby) {

            val escapeDestination = when{
                ghostX < arrayX && ghostY < arrayY -> ESCAPE.BR
                ghostX == arrayX && ghostY < arrayY -> ESCAPE.B
                ghostX > arrayX && ghostY < arrayY -> ESCAPE.BL

                ghostX < arrayX && ghostY == arrayY -> ESCAPE.R
                ghostX > arrayX && ghostY == arrayY -> ESCAPE.L

                ghostX < arrayX && ghostY > arrayY -> ESCAPE.TR
                ghostX == arrayX && ghostY > arrayY -> ESCAPE.T
                ghostX > arrayX && ghostY > arrayY -> ESCAPE.TL

                else -> ESCAPE.T
            }

            val newDestination = Tuple(escapeDestination.x, escapeDestination.y)

            path = AStarSearch.findAPath(array, getCurrentLocation(), newDestination)

        }

        //case: Current path is empty, must find a new pill to move towards
        if(path.isEmpty()){

            val nextPillLocation = array.getClosestPillLocation(arrayX, arrayY)

            if(thereIsAnotherPill(nextPillLocation)) {
                path = AStarSearch.findAPath(array, getCurrentLocation(), nextPillLocation)
            }
        }

        //case: Is still on path, and there is no ghost nearby - simply continue without further action
    }

    private fun thereIsAnotherPill(pill: Tuple) = pill.first != 0 && pill.second != 0

    private fun checkCollision(){

        val node = array.getNode(arrayX, arrayY)

        if(node.pill != null){
            node.pill = null
        }
    }

    override fun render(g: Graphics2D) {

        super.render(g)

        //TODO - remove this path-rendering after debugging is done
        path.render(g)
    }
}























