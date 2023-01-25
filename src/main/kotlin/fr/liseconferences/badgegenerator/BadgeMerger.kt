package fr.liseconferences.badgegenerator

import java.awt.Color
import java.awt.Image
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.floor
import kotlin.math.round

class BadgeMerger(private val columnCount: Int, private val rowCount: Int, private val badges: Array<Image>, private val path: String) {

    private val width = BadgeGenerator.WIDTH * columnCount
    private val height = BadgeGenerator.HEIGHT * rowCount

    private val margin = 200

    fun merge() {
        for(x in 0 until floor((badges.size / (columnCount*rowCount)).toFloat()).toInt()+1) {
            val bImage = BufferedImage(width+(2*margin), height, BufferedImage.TYPE_INT_ARGB)
            val graphics = bImage.createGraphics()
            graphics.setRenderingHints(RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON))

            val badgeMergeFile = File(path, "A4_$x.png")

            for(i in 0 until rowCount) {
                for(j in 0 until columnCount) {
                    val index = (i*columnCount + j)+(x*(columnCount*rowCount))
                    if(badges.size > index) {
                        val img = badges[index]
                        graphics.drawImage(img, margin+j*BadgeGenerator.WIDTH, i*BadgeGenerator.HEIGHT, BadgeGenerator.WIDTH, BadgeGenerator.HEIGHT, null)
                    }
                }
            }
            graphics.dispose()

            ImageIO.write(bImage, "png", badgeMergeFile)
        }
    }
}