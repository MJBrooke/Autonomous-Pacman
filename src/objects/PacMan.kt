package objects

import framework.*
import java.awt.Color
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
    val movementCost = 1
    val path = findAPill(array, Tuple(arrayX, arrayY), Tuple(16, 2))
    var nextSquareToMoveTo = path.remove()

    override fun tick(objects: LinkedList<GameObject>) {

        updateGridPosition()
        updateDirectionOfMovement()
        move()
        checkCollision(objects)

        println("\n")
    }

    private fun updateGridPosition(){
        if(x % 32 == 0F) //Ensures that pacman's position is only updated when his top left hand corner is fully in the next block
            arrayX = (x/32).toInt()

        if(y % 32 == 0F)
            arrayY = (y/32).toInt()
    }

    private fun checkCollision(objects: LinkedList<GameObject>){

        var objectToRemove: GameObject? = null

        objects.forEach {
            if(it.id == ObjectID.PILL) {
                if (it.arrayX == arrayX && it.arrayY == arrayY) {
                    objectToRemove = it
                }
            }
        }

        objects.remove(objectToRemove)
    }

    fun updateDirectionOfMovement(){

        if(arrayX == nextSquareToMoveTo.x && arrayY == nextSquareToMoveTo.y) {
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

    fun move(){
//        print("In Move\t")
//        print("Right? $right\t")
//        print("Left? $left\t")
//        print("Up? $up\t")
//        println("Down? $down\t")
        velX = 2F

        when{
            right -> x += velX
            left -> x -= velX
            up -> y -= velX
            down -> y += velX
        }
    }

    override fun render(g: Graphics2D) {
        affineTransform = AffineTransform()
        affineTransform.translate(x.toDouble(), y.toDouble())
        affineTransform.scale(1.0, 1.0)
        g.drawImage(image, affineTransform, null)

        //TODO - remove this rendering after debugging is done
        //Render path
        g.color = Color.BLUE
        path.forEach{
            g.drawRect(it.x*32, it.y*32, 24, 24)
        }

        //TODO - move this rendering to the game array, and then into Game.kt

        g.color = Color.green
        for(xx in 0..GameArray.WIDTH-1){
            for(yy in 0..GameArray.HEIGHT-1){
                val node = array.getNode(xx,yy)
                if(node.wall)
                    g.fillRect(node.x*32, node.y*32, 32,32)
            }
        }
    }

    override fun getBounds(): Rectangle {
        rectangle.x = x.toInt()
        rectangle.y = y.toInt()
        return rectangle
    }

    //TODO - pull into its own class
    private fun findAPill(graph: GameArray, fromTuple: Tuple, toTuple: Tuple): LinkedList<Node>{
        val open = LinkedList<Node>()
        val closed = LinkedList<Node>()
        val goalFound = false
        val startNode = graph.getNode(fromTuple)
        val goalNode = graph.getNode(toTuple)
        var currentNode: Node

        fun getLowestFNodeFromOpenList(): Node{

            var lowestFNode = open.first

            open.forEach {
                if(lowestFNode.nodeCostF > it.nodeCostF)
                    lowestFNode = it
            }

            return lowestFNode
        }

        fun swapNodeFromOpenToClosedList(node: Node){
            closed.add(node)
            open.remove(node)
        }

        fun isAtGoal(node: Node) = (node.x == toTuple.first) && (node.y == toTuple.second)

        fun getNeighbours(node: Node): LinkedList<Node>{

            val neighbours = LinkedList<Node>()

            with(node) {
                if(y > 0){
                    val neighbour = graph.getNode(x, y-1)
                    if(!neighbour.wall)
                        neighbours.add(neighbour)
                }

                if(x > 0) {
                    val neighbour = graph.getNode(x - 1, y)
                    if(!neighbour.wall)
                        neighbours.add(neighbour)
                }

                if(x < GameArray.WIDTH) {
                    val neighbour = graph.getNode(x + 1, y)
                    if(!neighbour.wall)
                        neighbours.add(neighbour)
                }

                if(y < GameArray.HEIGHT) {
                    val neighbour = graph.getNode(x, y + 1)
                    if(!neighbour.wall)
                        neighbours.add(neighbour)
                }
            }
            return neighbours
        }

        fun getDistanceBetweenNodes(from: Node, to: Node): Float =
                (Math.abs(from.x - to.x) + Math.abs(from.y - to.y)).toFloat()

        fun getPathToGoal(start: Node, goal: Node): LinkedList<Node>{
            val path = LinkedList<Node>()
            var pathCompleted = false
            var currNode: Node? = goal

            while(!pathCompleted){
                path.addFirst(currNode)
                currNode = currNode!!.parentNode

                if(currNode!!.x == start.x && currNode.y == start.y)
                    pathCompleted = true
            }

            return path
        }

        open.add(startNode)

        while(!goalFound){

            currentNode = getLowestFNodeFromOpenList()
            swapNodeFromOpenToClosedList(currentNode)

            if(isAtGoal(currentNode)) {
                println("Goal found - constructing path...")
                return getPathToGoal(startNode, currentNode)
            }

            val neighbours = getNeighbours(currentNode)

            for(neighbour in neighbours){

                if(open.contains(neighbour)){
                    if(neighbour.costToGetHereSoFarG > currentNode.costToGetHereSoFarG + movementCost){
                        neighbour.costToGetHereSoFarG = currentNode.costToGetHereSoFarG + movementCost
                        neighbour.nodeCostF = neighbour.distanceToGoalH + neighbour.costToGetHereSoFarG
                        neighbour.parentNode = currentNode
                    }
                } else {
                    if(!closed.contains(neighbour)) {
                        neighbour.distanceToGoalH = getDistanceBetweenNodes(neighbour, goalNode)
                        neighbour.costToGetHereSoFarG = currentNode.costToGetHereSoFarG + movementCost
                        neighbour.nodeCostF = neighbour.distanceToGoalH + neighbour.costToGetHereSoFarG
                        neighbour.parentNode = currentNode
                        open.add(neighbour)
                    }
                }
            }
            if(open.isEmpty())
                return LinkedList()
        }
        return LinkedList()
    }
}























