package framework

data class Node(
    var x: Int = 0,
    var y: Int = 0,

    var pill: Boolean = false,

    var parentNode: Node? = null,

    var nodeCostF: Float = 0F,
    var costToGetHereSoFarG: Float = 0F,
    var distanceToGoalH: Float = 0F
)
