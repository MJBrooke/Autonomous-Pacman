package framework.search

import framework.obj.GameArray
import framework.obj.Node
import framework.obj.Tuple
import java.util.*

object AStarSearch {

    val movementCost = 1

    fun findAPath(graph: GameArray, fromTuple: Tuple, toTuple: Tuple): LinkedList<Node> {
        val open = LinkedList<Node>()
        val closed = LinkedList<Node>()
        val goalFound = false
        val startNode = graph.getNode(fromTuple)
        val goalNode = graph.getNode(toTuple)
        var currentNode: Node

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

        fun isAtGoal(node: Node) = (node.x == toTuple.first) && (node.y == toTuple.second)

        fun getNeighbours(node: Node): LinkedList<Node> {

            val neighbours = LinkedList<Node>()

            with(node) {
                if(y > 0){
                    val neighbour = graph.getNode(x, y - 1)
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

        fun getDistanceBetweenNodes(from: Node, to: Node) =
                (Math.abs(from.x - to.x) + Math.abs(from.y - to.y)).toFloat()

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