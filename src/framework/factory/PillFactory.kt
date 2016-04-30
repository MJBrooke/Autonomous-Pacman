package framework.factory

import framework.obj.ObjectID
import objects.Pill

object PillFactory {

    fun getPill(x: Int, y: Int) = Pill(x, y, ObjectID.PILL)
}