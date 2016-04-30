package framework.factory

import framework.obj.Node

object NodeFactory {

    fun getWallNode(x: Int, y: Int) = Node(x, y, true)

    fun getOpenNode(x: Int, y: Int) = Node(x, y, false)
}