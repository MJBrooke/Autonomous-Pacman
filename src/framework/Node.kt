package framework

import java.util.*

/**
 * Created by michaelbrooke on 2016/04/24.
 */
data class Node(
    var x: Int,
    var y: Int,

    var pill: Boolean,

    var parentNode: Node,

    var nodeCostF: Float,
    var costToGetHereSoFarG: Float,
    var distanceToGoalH: Float
)
