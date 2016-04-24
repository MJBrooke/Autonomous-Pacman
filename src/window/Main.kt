package window

/**
 * Created by michaelbrooke on 2016/04/23.
 */

const val GAME_WIDTH = 832
const val GAME_HEIGHT = 928

fun main(args: Array<String>){
    GameWindow("PacMan", Game(), GAME_WIDTH, GAME_HEIGHT)
}