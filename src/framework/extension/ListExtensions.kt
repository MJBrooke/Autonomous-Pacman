package framework.extension

import framework.obj.Node
import java.awt.Color
import java.awt.Graphics2D
import java.util.*

fun LinkedList<Node>.render(g: Graphics2D){
    g.color = Color.GREEN
    forEach{
        g.drawRect(it.x*32, it.y*32, 24, 24)
    }
}
