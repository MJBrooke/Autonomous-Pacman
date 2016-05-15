package objects

import framework.obj.*
import framework.search.AStar
import framework.search.DIRECTION
import java.awt.Color
import java.awt.Graphics2D
import java.io.File
import java.util.*
import javax.imageio.ImageIO

class PacMan(arrayX: Int, arrayY: Int, array: GameArray, handler: ObjectHandler) : Sprite(arrayX, arrayY, array){

    var lives = 3

    val proximitySensitivity = 2

    val imgUp = ImageIO.read(File("res/PacmanUp.png"))
    val imgDown = ImageIO.read(File("res/PacmanDown.png"))
    val imgLeft = ImageIO.read(File("res/PacmanLeft.png"))
    val imgRight = ImageIO.read(File("res/PacmanRight.png"))

    val objects = handler.objects

    val AStarSearch = AStar(array, objects)

    init {
        createInitialPath()
    }

    private fun createInitialPath(){
        path = AStarSearch.findAPathToPill(Tuple(arrayX, arrayY), Tuple(15, 23))
        nextSquareToMoveTo = path.remove()
    }

    override fun tick() {
        super.tick()
        checkCollision()
    }

    override fun render(g: Graphics2D) {

        super.render(g)

        when(prevDir){
            DIRECTION.UP -> g.drawImage(imgUp, affineTransform, null)
            DIRECTION.DOWN -> g.drawImage(imgDown, affineTransform, null)
            DIRECTION.LEFT -> g.drawImage(imgLeft, affineTransform, null)
            DIRECTION.RIGHT -> g.drawImage(imgRight, affineTransform, null)
        }


        g.color = Color.yellow
        g.drawString("$lives", 5F, 15F)

        //TODO - remove this path-rendering after debugging is done
        //path.render(g)
    }

    override fun updatePath(){
        checkForGhosts()
        checkIfCurrentPathIsFinished()
    }

    private fun checkForGhosts(){
        objects.forEach {
            if(it is Ghost)
                if(ghostNearby(it))
                    getNewFleePath(it)
        }
    }

    private fun ghostNearby(ghost: Ghost) = ghostIsNearbyOnXAxis(ghost) && ghostIsNearbyOnYAxis(ghost)

    private fun ghostIsNearbyOnXAxis(ghost: Ghost) = ghost.arrayX >= (arrayX - proximitySensitivity) && ghost.arrayX <= (arrayX + proximitySensitivity)

    private fun ghostIsNearbyOnYAxis(ghost: Ghost) = ghost.arrayY >= (arrayY - proximitySensitivity) && ghost.arrayY <= (arrayY + proximitySensitivity)

    private fun getNewFleePath(ghost: Ghost){
        array.clearValues()
        path = AStarSearch.findAPathToPill(getCurrentLocation(), getFleeLocation(ghost))
    }

    fun checkIfCurrentPathIsFinished(){
        if(path.isEmpty()){
            getNewPillPath()
        }
    }

    fun getNewPillPath(){
        array.clearValues()

        val nextPillLocation = array.getClosestPillLocation(arrayX, arrayY)

        if(thereIsAnotherPill(nextPillLocation)) {
            path = AStarSearch.findAPathToPill(getCurrentLocation(), nextPillLocation)
        }
    }

    fun getFleeLocation(ghost: Ghost): Tuple =
            when {
                ghostIsAboveLeft(ghost) -> getValidFleeLocation(15, 26, 20, 29)//BR
                ghostIsAbove(ghost) -> getValidFleeLocation(1, 26, 20, 29)//B
                ghostIsAboveRight(ghost) -> getValidFleeLocation(1, 12, 20, 29)//BL

                ghostIsLeft(ghost) -> getValidFleeLocation(15, 26, 1, 29)//R
                ghostIsRight(ghost) -> getValidFleeLocation(1, 11, 1, 29)//L

                ghostIsBelowLeft(ghost) -> getValidFleeLocation(15, 26, 1, 8)//TR
                ghostIsBelow(ghost) -> getValidFleeLocation(1, 26, 1, 14)//T
                ghostIsBelowRight(ghost) -> getValidFleeLocation(1, 12, 1, 8)//TL

                else -> Tuple(1,1)
            }

    private fun ghostIsAboveLeft(ghost: Ghost) = ghost.arrayX < arrayX && ghost.arrayY < arrayY
    private fun ghostIsAbove(ghost: Ghost) = ghost.arrayX == arrayX && ghost.arrayY < arrayY
    private fun ghostIsAboveRight(ghost: Ghost) = ghost.arrayX > arrayX && ghost.arrayY < arrayY

    private fun ghostIsLeft(ghost: Ghost) = ghost.arrayX < arrayX && ghost.arrayY == arrayY
    private fun ghostIsRight(ghost: Ghost) = ghost.arrayX > arrayX && ghost.arrayY == arrayY

    private fun ghostIsBelowLeft(ghost: Ghost) = ghost.arrayX < arrayX && ghost.arrayY > arrayY
    private fun ghostIsBelow(ghost: Ghost) = ghost.arrayX == arrayX && ghost.arrayY > arrayY
    private fun ghostIsBelowRight(ghost: Ghost) = ghost.arrayX > arrayX && ghost.arrayY > arrayY

    private fun getValidFleeLocation(lowX: Int, highX: Int, lowY: Int, highY: Int): Tuple {

        var validLocationFound = false
        var fleeX = 0
        var fleeY = 0

        while(!validLocationFound){
            fleeX = getRandomNumberInRange(lowX, highX)
            fleeY = getRandomNumberInRange(lowY, highY)

            if(isValidLocation(fleeX, fleeY))
                validLocationFound = true
        }

        return Tuple(fleeX, fleeY)
    }

    private fun isValidLocation(fleeX: Int, fleeY: Int) =
            !array.getNode(fleeX, fleeY).wall && fleeX != arrayX && fleeY != arrayY

    private fun getRandomNumberInRange(low: Int, high: Int) =
        Random().nextInt(high-low)+low

    private fun thereIsAnotherPill(pill: Tuple) = pill.first != 0 && pill.second != 0

    private fun checkCollision(){
        checkForCollisionWithPill()
        checkForCollisionWithGhost()
    }

    private fun checkForCollisionWithPill(){
        val node = array.getNode(arrayX, arrayY)

        if(node.pill != null){
            node.pill = null
        }
    }

    private fun checkForCollisionWithGhost(){
        objects.forEach {
            if(it is Ghost){
                if(arrayX == it.arrayX && arrayY == it.arrayY){
                    resetPacman()
                }
            }
        }
    }

    private fun resetPacman(){
        lives--
        arrayX = 14
        arrayY = 23
        x = arrayX*32F
        y = arrayY*32F
        path = LinkedList()
        nextSquareToMoveTo = Node(14,23)
    }
}























