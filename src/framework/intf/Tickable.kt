package framework.intf

import framework.obj.GameObject
import java.util.*

interface Tickable {

    fun tick(objects: LinkedList<GameObject>)
}