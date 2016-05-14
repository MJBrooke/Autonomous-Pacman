package objects

import framework.obj.GameObject
import java.awt.Color
import java.awt.Graphics2D

class Pill(arrayX: Int, arrayY: Int) : GameObject(arrayX, arrayY){

    init {
        width = 6
        height = 6

        x = getPillCenterValue(arrayX)
        y = getPillCenterValue(arrayY)
    }

    override fun tick() {}

    override fun render(g: Graphics2D) {
        g.color = Color.white
        g.fillRect(x.toInt(), y.toInt(), width, height)
    }

    //Position * tile size + half the sprite width/height - half sprite size
    private fun getPillCenterValue(pos: Int) = (pos*32+16-3).toFloat()
}