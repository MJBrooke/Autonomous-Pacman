package framework.factory

import objects.Pill

object PillFactory {

    fun getPill(x: Int, y: Int) = Pill(x, y)
}