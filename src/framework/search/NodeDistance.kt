package framework.search

import framework.obj.Node

object NodeDistance {

    @JvmStatic fun getDistanceBetweenNodes(from: Node, to: Node) =
            (Math.abs(from.x - to.x) + Math.abs(from.y - to.y)).toFloat()
}