package objects

import com.sun.javafx.iio.ImageStorage
import framework.*
import window.GAME_WIDTH
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO

/**
 * Created by michaelbrooke on 2016/04/24.
 */
class PacMan(x: Float, y: Float, id: ObjectID) : GameObject(x, y, id){

    val width = 32
    val height = 32
    val rectangle = Rectangle(width, height)
    val image = ImageIO.read(File("res/Pacman.png"))

    init{
        right = true
    }

    override fun tick(objects: LinkedList<GameObject>) {

//        velX = 5F

        if(x >= (GAME_WIDTH-32) || x <= 0){
            changeDirection()
        }

        if(right)
            velX = 3F
        else if(left)
            velX = -3F

        x += velX

        checkCollision(objects)
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

//        var currentNode = startNode

//        while(true){
//
//            //Ensure that our current node is the one with the lowest cost in the current frontier
//            frontier.forEach {
//                if(currentNode.nodeCostF > it.nodeCostF)
//                    currentNode = it
//            }
//
//            //If we have found our goal, we need to stop messing with the frontier and explored lists
//            if(currentNode == goalNode)
//                break
//
//            //Otherwise we move the explored node to the explored list
//            frontier.remove(currentNode)
//            explored.add(currentNode)
//
//            //We look through the neighbours of out current node
//            currentNode.neighbours.forEach {
//                var costToNextNode = it.nodeCostF +
//            }
//
//        }



        //TODO - Make this the proper list
        return LinkedList<Node>()
    }

    private fun changeDirection(){
        right = !right
        left = !left
    }

    private fun checkCollision(objects: LinkedList<GameObject>){
        objects.forEach {
            if(it.id == ObjectID.PILL)
                if(getBounds().intersects(it.getBounds()))
                    objects.remove(it)
        }
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