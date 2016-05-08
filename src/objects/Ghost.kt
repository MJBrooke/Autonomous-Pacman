package objects

import framework.obj.*
import framework.search.AStarSearch
import java.io.File
import javax.imageio.ImageIO

class Ghost(arrayX: Int, arrayY: Int, array: GameArray, val pacman: PacMan) : Sprite(arrayX, arrayY, ObjectID.GHOST, array) {

    init {
        image = ImageIO.read(File("res/BlinkyGhost.png"))
        path = AStarSearch.findAPath(array, getCurrentLocation(), pacman.getCurrentLocation())
        nextSquareToMoveTo = path.remove()
    }

    override fun updatePath() {
        if(path.isEmpty())
            path = AStarSearch.findAPath(array, getCurrentLocation(), pacman.getCurrentLocation())
    }
}