package framework.obj

import framework.search.AStar
import objects.PacMan
import java.awt.image.BufferedImage

abstract class Ghost(arrayX: Int, arrayY: Int, array: GameArray, val pacman: PacMan) : Sprite(arrayX, arrayY, array) {
    lateinit var image: BufferedImage
    val AStarSearch = AStar(array)

    init{
        velocity = 2F
    }
}