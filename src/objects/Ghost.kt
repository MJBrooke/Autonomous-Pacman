package objects

import framework.obj.*
import framework.search.AStarSearch
import java.io.File
import java.util.*
import javax.imageio.ImageIO

class Ghost(arrayX: Int, arrayY: Int, array: GameArray, val pacman: PacMan) : Sprite(arrayX, arrayY, ObjectID.GHOST, array) {

    init {
        velocity = 4F
        image = ImageIO.read(File("res/BlinkyGhost.png"))
        path = AStarSearch.findAPath(array, getCurrentLocation(), pacman.getCurrentLocation(), prevDir)
        nextSquareToMoveTo = path.remove()
    }

    override fun updatePath(objects: LinkedList<GameObject>) {
        if(path.isEmpty())
            path = AStarSearch.findAPath(array, getCurrentLocation(), pacman.getCurrentLocation(), prevDir)
    }
}