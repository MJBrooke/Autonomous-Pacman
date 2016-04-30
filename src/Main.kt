import framework.obj.GameArray
import window.Game
import window.GameWindow

const val GAME_WIDTH_PIXELS = GameArray.WIDTH*28-10
const val GAME_HEIGHT_PIXELS = GameArray.HEIGHT*31-10

fun main(args: Array<String>){
    GameWindow("PacMan", Game(), GAME_WIDTH_PIXELS, GAME_HEIGHT_PIXELS)
}