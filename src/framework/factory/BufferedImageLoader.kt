package framework.factory

import java.io.File
import javax.imageio.ImageIO

object   BufferedImageLoader {

    fun getImage(path: String) = ImageIO.read(File(path))
}