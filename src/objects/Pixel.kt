package objects

class Pixel(rgbValue: Int){

    val r = getRedColorComponent(rgbValue)
    val g = getGreenColorComponent(rgbValue)
    val b = getBlueColorComponent(rgbValue)

    private fun getRedColorComponent(pixel: Int) = (pixel shr 16) and 0xff
    private fun getGreenColorComponent(pixel: Int) = (pixel shr 8) and 0xff
    private fun getBlueColorComponent(pixel: Int) = pixel and 0xff

    fun isWall() = r == 0 && g == 0 && b == 160
    fun isPill() = r == 0 && g == 128 && b == 0
}