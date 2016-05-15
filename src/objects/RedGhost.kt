package objects

import framework.obj.*
import java.awt.Graphics2D
import java.io.File
import javax.imageio.ImageIO

class RedGhost(arrayX: Int, arrayY: Int, array: GameArray, pacman: PacMan) : Ghost(arrayX, arrayY, array, pacman) {

    init {
        image = ImageIO.read(File("res/BlinkyGhost.png"))
        path = AStarSearch.findAPathToPacMan(getCurrentLocation(), pacman.getCurrentLocation(), prevDir)
        nextSquareToMoveTo = path.remove()
    }

    override fun updatePath() {
        path = AStarSearch.findAPathToPacMan(getCurrentLocation(), pacman.getCurrentLocation(), prevDir)
    }

    override fun render(g: Graphics2D){
        super.render(g)
        g.drawImage(image, affineTransform, null)
    }

}