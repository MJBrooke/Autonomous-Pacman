package framework.obj

import java.awt.Graphics2D
import java.util.*

class ObjectHandler() {

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
}