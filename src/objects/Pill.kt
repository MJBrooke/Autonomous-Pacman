package objects

import framework.obj.GameObject
import framework.obj.ObjectID
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Rectangle
import java.util.*

class Pill(arrayX: Int, arrayY: Int, id: ObjectID) : GameObject(arrayX, arrayY, id){

    val rectangle = Rectangle(width, height)

    init {
        width = 6
        height = 6

        x = getPillCenterValue(arrayX)
        y = getPillCenterValue(arrayY)
    }

    override fun tick(objects: LinkedList<GameObject>) {

    }

    override fun render(g: Graphics2D) {
        g.color = Color.white
        g.fillRect(x.toInt(), y.toInt(), width, height)
    }

    override fun getBounds(): Rectangle {
        rectangle.x = x.toInt()
        rectangle.y = y.toInt()
        return rectangle
    }

    private fun getPillCenterValue(pos: Int): Float{
        //Position * tile size + half the sprite width/height - half sprite size
        return (pos*32+16-3).toFloat()
    }
}