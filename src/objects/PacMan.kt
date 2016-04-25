package objects

import framework.*
import window.GAME_WIDTH
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.geom.AffineTransform
import java.io.File
import java.util.*
import javax.imageio.ImageIO

class PacMan(arrayX: Int, arrayY: Int, x: Float, y: Float, id: ObjectID, val array: GameArray) : GameObject(arrayX, arrayY, x, y, id){

    val width = 32
    val height = 32
    val rectangle = Rectangle(width, height)
    val image = ImageIO.read(File("res/Pacman.png"))

    init{
        right = true
    }

    override fun tick(objects: LinkedList<GameObject>) {

        if(x >= (GAME_WIDTH-32) || x <= 0){
            changeDirection()
        }

        if(right)
            velX = 3F
        else if(left)
            velX = -3F

        x += velX

        updateGridPosition()
        checkCollision(objects)
    }

    private fun updateGridPosition(){
        arrayX = (x/32).toInt()
        arrayY = (y/32).toInt()
//        println("($arrayX, $arrayY)")
    }

    private fun findPill(graph: GameArray, fromTuple: Tuple, toTuple: Tuple): LinkedList<Node>{

        //Function for getting the distance between 2 nodes
        fun getDistanceToGoal(from: Node, to: Node): Float =
                (Math.abs(from.x - to.x) + Math.abs(from.y - to.y)).toFloat()

        //Create lists to hold the un/explored game world
        val frontier = LinkedList<Node>()
        val explored = LinkedList<Node>()

        //Get the goal and start nodes from the game array
        val goalNode = graph.getNode(toTuple)
        val startNode = graph.getNode(fromTuple)

        //Setup the initial node's values for the A* search
        startNode.costToGetHereSoFarG = 0F
        startNode.distanceToGoalH = getDistanceToGoal(startNode, goalNode)
        startNode.nodeCostF = startNode.distanceToGoalH

        //The start node is the first node that will be explored
        frontier.add(startNode)

        var currentNode = startNode

        while(!frontier.isEmpty()){

            //Find the node with the lowest heuristic cost in the open list
            frontier.forEach {
                if(currentNode.nodeCostF > it.nodeCostF)
                    currentNode = it
            }

            frontier.remove(currentNode)


        }

        //TODO - Make this the proper list
        return LinkedList<Node>()
    }

    private fun changeDirection(){
        right = !right
        left = !left
    }

    private fun checkCollision(objects: LinkedList<GameObject>){

        var objectToRemove: GameObject? = null

        objects.forEach {
            if(it.id == ObjectID.PILL) {
                if (it.arrayX == arrayX && it.arrayY == arrayY) {
                    objectToRemove = it

                    val pill = it as Pill
                    with(pill) {
                        array.togglePill(arrayX, arrayY)
                    }
                }
            }
        }

        objects.remove(objectToRemove)

//        var objectToRemove: GameObject? = null
//
//        objects.forEach {
//            if(it.id == ObjectID.PILL) {
//                if (getBounds().intersects(it.getBounds())) {
//                    objectToRemove = it
//
//                    val pill = it as Pill
//                    with(pill) {
//                        array.togglePill(arrayX, arrayY)
//                    }
//                }
//            }
//        }
//
//        objects.remove(objectToRemove)
    }

    override fun render(g: Graphics2D) {
        affineTransform = AffineTransform()
        affineTransform.translate(x.toDouble(), y.toDouble())
        affineTransform.scale(1.0, 1.0)
        g.drawImage(image, affineTransform, null)
    }

    override fun getBounds(): Rectangle {
        rectangle.x = x.toInt()
        rectangle.y = y.toInt()
        return rectangle
    }
}