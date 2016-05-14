package framework.obj

import framework.intf.Renderable
import framework.intf.Tickable
import java.awt.Graphics2D
import java.awt.geom.AffineTransform

abstract class GameObject(var arrayX: Int, var arrayY: Int): Tickable, Renderable {

    lateinit var affineTransform: AffineTransform

    var x = arrayX*32F
    var y = arrayY*32F

    var width = 0
    var height = 0

    abstract override fun tick()

    abstract override fun render(g: Graphics2D)
}