package objects

import framework.obj.*
import framework.search.AStar
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class Ghost(arrayX: Int, arrayY: Int, array: GameArray, val pacman: PacMan) : Sprite(arrayX, arrayY, array) {

    val image: BufferedImage = ImageIO.read(File("res/BlinkyGhost.png"))
    val AStarSearch = AStar(array)

    init {
        velocity = 2F

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

//    fun findAPath(fromTuple: Tuple, toTuple: Tuple, dir: DIRECTION): LinkedList<Node> {
//        val open = LinkedList<Node>()
//        val closed = LinkedList<Node>()
//        val goalFound = false
//        val startNode = array.getNode(fromTuple)
//        val goalNode = array.getNode(toTuple)
//        var currentNode: Node
//        var firstRetrievalOfNeighbours = true
//
//        fun getLowestFNodeFromOpenList(): Node {
//
//            var lowestFNode = open.first
//
//            open.forEach {
//                if(lowestFNode.nodeCostF > it.nodeCostF)
//                    lowestFNode = it
//            }
//
//            return lowestFNode
//        }
//
//        fun swapNodeFromOpenToClosedList(node: Node){
//            closed.add(node)
//            open.remove(node)
//        }
//
//        fun isAtGoal(node: Node) = (node.x == toTuple.first) && (node.y == toTuple.second)
//
//        fun getInitialNeighbours(node: Node): LinkedList<Node>{
//
//            val neighbours = LinkedList<Node>()
//
//            with(node) {
//                if(y > 0){ //ABOVE
//                    if(dir != DIRECTION.DOWN) {
//                        val neighbour = array.getNode(x, y - 1)
//                        if (!neighbour.wall)
//                            neighbours.add(neighbour)
//                    }
//                }
//
//                if(x > 0) { //LEFT
//                    if(dir != DIRECTION.RIGHT) {
//                        val neighbour = array.getNode(x - 1, y)
//                        if (!neighbour.wall)
//                            neighbours.add(neighbour)
//                    }
//                }
//
//                if(x < GameArray.WIDTH) {//RIGHT
//                    if(dir != DIRECTION.LEFT) {
//                        val neighbour = array.getNode(x + 1, y)
//                        if (!neighbour.wall)
//                            neighbours.add(neighbour)
//                    }
//                }
//
//                if(y < GameArray.HEIGHT) {//BELOW
//                    if(dir != DIRECTION.UP) {
//                        val neighbour = array.getNode(x, y + 1)
//                        if (!neighbour.wall)
//                            neighbours.add(neighbour)
//                    }
//                }
//            }
//
//            return neighbours
//        }
//
//        fun getNeighbours(node: Node): LinkedList<Node> {
//
//            val neighbours = LinkedList<Node>()
//
//            with(node) {
//                if(y > 0){
//                    val neighbour = array.getNode(x, y - 1)
//                    if(!neighbour.wall)
//                        neighbours.add(neighbour)
//                }
//
//                if(x > 0) {
//                    val neighbour = array.getNode(x - 1, y)
//                    if(!neighbour.wall)
//                        neighbours.add(neighbour)
//                }
//
//                if(x < GameArray.WIDTH) {
//                    val neighbour = array.getNode(x + 1, y)
//                    if(!neighbour.wall)
//                        neighbours.add(neighbour)
//                }
//
//                if(y < GameArray.HEIGHT) {
//                    val neighbour = array.getNode(x, y + 1)
//                    if(!neighbour.wall)
//                        neighbours.add(neighbour)
//                }
//            }
//            return neighbours
//        }
//
//        fun getPathToGoal(start: Node, goal: Node): LinkedList<Node> {
//            val path = LinkedList<Node>()
//            var pathCompleted = false
//            var currNode: Node? = goal
//
//            while(!pathCompleted){
//                path.addFirst(currNode)
//                currNode = currNode!!.parentNode
//
//                if(currNode!!.x == start.x && currNode.y == start.y)
//                    pathCompleted = true
//            }
//
//            return path
//        }
//
//        if(isAtGoal(startNode)) {
//            val list = LinkedList<Node>()
//            list.addFirst(startNode)
//            return list
//        }
//
//        open.add(startNode)
//
//        while(!goalFound){
//
//            currentNode = getLowestFNodeFromOpenList()
//            swapNodeFromOpenToClosedList(currentNode)
//
//            if(isAtGoal(currentNode)) {
//                return getPathToGoal(startNode, currentNode)
//            }
//
//            val neighbours = if(firstRetrievalOfNeighbours){
//                firstRetrievalOfNeighbours = false
//                getInitialNeighbours(currentNode)
//            }
//            else
//                getNeighbours(currentNode)
//
//            for(neighbour in neighbours){
//
//                if(open.contains(neighbour)){
//                    if(neighbour.costToGetHereSoFarG > currentNode.costToGetHereSoFarG + AStarSearch.movementCost){
//                        neighbour.costToGetHereSoFarG = currentNode.costToGetHereSoFarG + AStarSearch.movementCost
//                        neighbour.nodeCostF = neighbour.distanceToGoalH + neighbour.costToGetHereSoFarG
//                        neighbour.parentNode = currentNode
//                    }
//                } else {
//                    if(!closed.contains(neighbour)) {
//                        neighbour.distanceToGoalH = AStarSearch.getDistanceBetweenNodes(neighbour, goalNode)
//                        neighbour.costToGetHereSoFarG = currentNode.costToGetHereSoFarG + AStarSearch.movementCost
//                        neighbour.nodeCostF = neighbour.distanceToGoalH + neighbour.costToGetHereSoFarG
//                        neighbour.parentNode = currentNode
//                        open.add(neighbour)
//                    }
//                }
//            }
//            if(open.isEmpty())
//                return LinkedList()
//        }
//        return LinkedList()
//    }
}