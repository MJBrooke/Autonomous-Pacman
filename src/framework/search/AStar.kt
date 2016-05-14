package framework.search

import framework.obj.GameArray
import framework.obj.GameObject
import framework.obj.Node
import framework.obj.Tuple
import objects.Ghost
import java.util.*

class AStar(val array: GameArray, val objects: LinkedList<GameObject> = LinkedList()) {

    val open = LinkedList<Node>()
    val closed = LinkedList<Node>()
    val goalFound = false
    lateinit var startNode: Node
    lateinit var goalNode: Node
    lateinit var currentNode: Node
    var ghost: Ghost? = null

    fun findAPathToPacMan(fromTuple: Tuple, toTuple: Tuple, dir: DIRECTION): LinkedList<Node> {
        open.clear()
        closed.clear()

        startNode = array.getNode(fromTuple)
        goalNode = array.getNode(toTuple)
        var firstRetrievalOfNeighbours = true

        if(isAtGoal(startNode, toTuple)) {
            val list = LinkedList<Node>()
            list.addFirst(startNode)
            return list
        }

        open.add(startNode)

        while(!goalFound){

            currentNode = getLowestFNodeFromOpenList()
            swapNodeFromOpenToClosedList(currentNode)

            if(isAtGoal(currentNode, toTuple)) {
                return getPathToGoal(startNode, currentNode)
            }

            val neighbours = if(firstRetrievalOfNeighbours){
                firstRetrievalOfNeighbours = false
                getInitialNeighbours(currentNode, dir)
            }
            else
                getNeighbours(currentNode)

            for(neighbour in neighbours){

                if(open.contains(neighbour)){
                    if(neighbour.costToGetHereSoFarG > currentNode.costToGetHereSoFarG + AStarSearch.movementCost){
                        neighbour.costToGetHereSoFarG = currentNode.costToGetHereSoFarG + AStarSearch.movementCost
                        neighbour.nodeCostF = neighbour.distanceToGoalH + neighbour.costToGetHereSoFarG
                        neighbour.parentNode = currentNode
                    }
                } else {
                    if(!closed.contains(neighbour)) {
                        neighbour.distanceToGoalH = AStarSearch.getDistanceBetweenNodes(neighbour, goalNode)
                        neighbour.costToGetHereSoFarG = currentNode.costToGetHereSoFarG + AStarSearch.movementCost
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

    fun findAPathToPill(fromTuple: Tuple, toTuple: Tuple): LinkedList<Node> {

        open.clear()
        closed.clear()

        startNode = array.getNode(fromTuple)
        goalNode = array.getNode(toTuple)

        //TODO - this code is whack. Maybe pass actual references of each ghost?
        objects.forEach {
            if (it is Ghost) {
                ghost = it
            }
        }

        if(isAtGoal(startNode, toTuple)) {
            val list = LinkedList<Node>()
            list.addFirst(startNode)
            return list
        }

        open.add(startNode)

        while(!goalFound){

            currentNode = getLowestFNodeFromOpenList()
            swapNodeFromOpenToClosedList(currentNode)

            if(isAtGoal(currentNode, toTuple)) {
                return getPathToGoal(startNode, currentNode)
            }

            val neighbours = getNeighbours(currentNode)

            for(neighbour in neighbours){

                if(open.contains(neighbour)){
                    if(neighbour.costToGetHereSoFarG > currentNode.costToGetHereSoFarG + AStarSearch.movementCost){
                        neighbour.costToGetHereSoFarG = currentNode.costToGetHereSoFarG + AStarSearch.movementCost
                        neighbour.nodeCostF = neighbour.distanceToGoalH + neighbour.costToGetHereSoFarG

                        if(ghost != null) {
                            if (neighbour.x == ghost!!.arrayX && neighbour.y == ghost!!.arrayY)
                                neighbour.nodeCostF += 1000
                        }
                    }
                } else {
                    if(!closed.contains(neighbour)) {
                        neighbour.distanceToGoalH = AStarSearch.getDistanceBetweenNodes(neighbour, goalNode)
                        neighbour.costToGetHereSoFarG = currentNode.costToGetHereSoFarG + AStarSearch.movementCost
                        neighbour.nodeCostF = neighbour.distanceToGoalH + neighbour.costToGetHereSoFarG

                        if(ghost != null) {
                            if (neighbour.x == ghost!!.arrayX && neighbour.y == ghost!!.arrayY)
                                neighbour.nodeCostF += 1000
                        }

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

    fun getLowestFNodeFromOpenList(): Node {

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

    fun isAtGoal(node: Node, toTuple: Tuple) = (node.x == toTuple.first) && (node.y == toTuple.second)

    fun getInitialNeighbours(node: Node, dir: DIRECTION): LinkedList<Node>{

        val neighbours = LinkedList<Node>()

        with(node) {
            if(y > 0){ //ABOVE
                if(dir != DIRECTION.DOWN) {
                    val neighbour = array.getNode(x, y - 1)
                    if (!neighbour.wall)
                        neighbours.add(neighbour)
                }
            }

            if(x > 0) { //LEFT
                if(dir != DIRECTION.RIGHT) {
                    val neighbour = array.getNode(x - 1, y)
                    if (!neighbour.wall)
                        neighbours.add(neighbour)
                }
            }

            if(x < GameArray.WIDTH) {//RIGHT
                if(dir != DIRECTION.LEFT) {
                    val neighbour = array.getNode(x + 1, y)
                    if (!neighbour.wall)
                        neighbours.add(neighbour)
                }
            }

            if(y < GameArray.HEIGHT) {//BELOW
                if(dir != DIRECTION.UP) {
                    val neighbour = array.getNode(x, y + 1)
                    if (!neighbour.wall)
                        neighbours.add(neighbour)
                }
            }
        }

        return neighbours
    }

    fun getNeighbours(node: Node): LinkedList<Node> {

        val neighbours = LinkedList<Node>()

        with(node) {
            if(y > 0){
                val neighbour = array.getNode(x, y - 1)
                if(!neighbour.wall)
                    neighbours.add(neighbour)
            }

            if(x > 0) {
                val neighbour = array.getNode(x - 1, y)
                if(!neighbour.wall)
                    neighbours.add(neighbour)
            }

            if(x < GameArray.WIDTH) {
                val neighbour = array.getNode(x + 1, y)
                if(!neighbour.wall)
                    neighbours.add(neighbour)
            }

            if(y < GameArray.HEIGHT) {
                val neighbour = array.getNode(x, y + 1)
                if(!neighbour.wall)
                    neighbours.add(neighbour)
            }
        }
        return neighbours
    }

    fun getPathToGoal(start: Node, goal: Node): LinkedList<Node> {
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
}