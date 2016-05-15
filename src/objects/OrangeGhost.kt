package objects

import framework.obj.*
import java.awt.Graphics2D
import java.io.File
import java.util.*
import javax.imageio.ImageIO

class OrangeGhost(arrayX: Int, arrayY: Int, array: GameArray, pacman: PacMan) : Ghost(arrayX, arrayY, array, pacman) {

    val proximitySensitivity = 5
    val delayLength = 60*5

    var delayCounter = 0
    var isRedirecting = false

    init {
        image = ImageIO.read(File("res/OrangeGhost.png"))
        path = LinkedList()
        nextSquareToMoveTo = Node(arrayX, arrayY)
    }

    override fun updatePath() {

        if(delayCounter > delayLength && isRedirecting == false) { //Prevents Orange from attacking until 5 seconds has passed

            val locationToGoTo = if (pacManNearby()) {
                isRedirecting = true
                getRedirectLocation()
            } else
                pacman.getCurrentLocation()

            path = AStarSearch.findAPathToPacMan(getCurrentLocation(), locationToGoTo, prevDir)
        }

        if(delayCounter <= delayLength)
            delayCounter++
    }

    override fun getNextSquareToMoveTo(){
        super.getNextSquareToMoveTo()

        if(path.isEmpty())
            isRedirecting = false
    }

    private fun pacManNearby() = pacManIsNearbyOnXAxis() && pacManIsNearbyOnYAxis()

    private fun pacManIsNearbyOnXAxis() = pacman.arrayX >= (arrayX - proximitySensitivity) && pacman.arrayX <= (arrayX + proximitySensitivity)

    private fun pacManIsNearbyOnYAxis() = pacman.arrayY >= (arrayY - proximitySensitivity) && pacman.arrayY <= (arrayY + proximitySensitivity)

    private fun getRedirectLocation(): Tuple {
        var validLocationFound = false
        var newX = 0
        var newY = 0

        while(!validLocationFound){
            newX = getRandomNumberInRange(arrayX-5, arrayX+5)
            newY = getRandomNumberInRange(arrayY-5, arrayY+5)

            if(newX > 0 && newX < GameArray.WORLD_WIDTH && newY > 0 && newY < GameArray.WORLD_HEIGHT && !array.getNode(newX, newY).wall)
                validLocationFound = true
        }

        return Tuple(newX, newY)
    }

    private fun getRandomNumberInRange(low: Int, high: Int) =
            Random().nextInt(high-low)+low

    override fun render(g: Graphics2D){
        super.render(g)
        g.drawImage(image, affineTransform, null)
    }
}