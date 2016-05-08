package objects

import framework.extension.render
import framework.obj.*
import framework.search.AStarSearch
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

    override fun updatePath(){
        if(path.isEmpty()){

            val nextPillLocation = array.getClosestPillLocation(arrayX, arrayY)

            if(thereIsAnotherPill(nextPillLocation)) {
                path = AStarSearch.findAPath(array, Tuple(arrayX, arrayY), nextPillLocation)
            }
        }
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























