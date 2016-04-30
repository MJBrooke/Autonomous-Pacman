package framework.obj

class Node(
        var x: Int = 0,
        var y: Int = 0,

        var wall: Boolean = false,

        var parentNode: Node? = null,

        var nodeCostF: Float = 0F,
        var costToGetHereSoFarG: Float = 0F,
        var distanceToGoalH: Float = 0F
){
    override fun toString(): String{
        return "($x, $y)"
    }
}
