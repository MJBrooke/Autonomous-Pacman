package framework.obj

import framework.intf.Renderable
import framework.intf.Tickable
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.geom.AffineTransform
import java.util.*

abstract class GameObject(var arrayX: Int, var arrayY: Int, val id: ObjectID): Tickable, Renderable {

    lateinit var affineTransform: AffineTransform

    var x = arrayX*32F
    var y = arrayY*32F

    val velocity = 4F

    var width = 0
    var height = 0

    var left = false
    var right = false
    var up = false
    var down = false

    abstract override fun tick(objects: LinkedList<GameObject>)

    abstract override fun render(g: Graphics2D)

    abstract fun getBounds(): Rectangle
}