package window

import framework.GameObject
import java.awt.Graphics2D
import java.util.*

/**
 * Created by michaelbrooke on 2016/04/24.
 */
class ObjectHandler {

    val objects = LinkedList<GameObject>()

    fun tick(){
        objects.forEach {
            it.tick(objects)
        }
    }

    fun render(g: Graphics2D){
        objects.forEach {
            it.render(g)
        }
    }

    fun addGameObject(newObject: GameObject){
        objects.add(newObject)
    }

    fun removeGameObject(objectToRemove: GameObject){
        objects.remove(objectToRemove)
    }
}