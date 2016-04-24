package framework

import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.geom.AffineTransform
import java.util.*

/**
 * Created by michaelbrooke on 2016/04/24.
 */
abstract class GameObject(var x: Float, var y: Float, val id: ObjectID) {

    lateinit var affineTransform: AffineTransform

    var velX = 0F
    var velY = 0F

    var left = false
    var right = false
    var up = false
    var down = false

    abstract fun tick(objects: LinkedList<GameObject>)

    abstract fun render(g: Graphics2D)

    abstract fun getBounds(): Rectangle
}