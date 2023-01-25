package fr.liseconferences.badgegenerator

import java.awt.*
import java.awt.image.BufferedImage
import java.io.File
import java.io.InputStream
import javax.imageio.ImageIO

class BadgeGenerator(private val person: Person) {

    private val image = BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB)
    private val graphics = image.createGraphics()

    companion object {
        private val templateImage = ImageIO.read(Companion::class.java.classLoader.getResourceAsStream("lise_template.png"))
        const val WIDTH = 1385
        const val HEIGHT = 780
        private const val NAMES_SIZE = 120
        private const val COMPANY_SIZE = 75

        private const val COMPANY_MARGIN = 50

        private val logoImage = ImageIO.read(Companion::class.java.classLoader.getResourceAsStream("logo_noir.png"))
        private val eatLogo = ImageIO.read(Companion::class.java.classLoader.getResourceAsStream("repas.png"))
        private const val LOGO_SCALE_RATIO = 6

        private const val EAT_LOGO_SCALE_RATIO = 4
    }

    init {
        applyTemplate()
        applyLiseLogo()
        if(person.eat) {
            applyEat()
        }
        addNames()
        if(person.company != null) {
            applyCompany()
        }
    }

    private fun applyEat() {
        val width = eatLogo.width / EAT_LOGO_SCALE_RATIO
        val height = eatLogo.height / EAT_LOGO_SCALE_RATIO

        graphics.drawImage(eatLogo, 140, HEIGHT-height-50, width, height, null)
    }

    private fun applyCompany() {
        graphics.font = Font.createFonts(javaClass.classLoader.getResourceAsStream("cocogoose_light.ttf"))[0].deriveFont(
            COMPANY_SIZE.toFloat())
        val fontMetrics = graphics.fontMetrics

        val companyWidth = fontMetrics.stringWidth(person.company!!)
        val companyX = (WIDTH/2)-(companyWidth/2)
        val companyY = (HEIGHT/2)+(NAMES_SIZE/2)+COMPANY_MARGIN

        graphics.drawString(person.company, companyX, companyY)
    }

    private fun applyTemplate() {
        graphics.background = Color.white
        graphics.setRenderingHints(RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON))
        graphics.drawImage(templateImage, 0, 0, WIDTH, HEIGHT, null)
    }

    private fun addNames() {
        graphics.setRenderingHints(RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON))
        graphics.font = Font.createFonts(javaClass.classLoader.getResourceAsStream("cocogoose_semibold.ttf"))[0].deriveFont(
            NAMES_SIZE.toFloat())
        graphics.color = Color.BLACK
        val fontMetrics = graphics.fontMetrics

        var names = "${person.lastName.uppercase()} ${person.firstName.replaceFirstChar { if(it.isUpperCase()) it else it.uppercaseChar() }}"
        var namesWidth = fontMetrics.stringWidth(names)

        if(namesWidth > WIDTH) {
            names = "${person.lastName[0].uppercaseChar()} ${person.firstName.replaceFirstChar { if(it.isUpperCase()) it else it.uppercaseChar() }}"
            namesWidth = fontMetrics.stringWidth(names)
        }

        val nameX = (WIDTH/2)-(namesWidth/2)
        val nameY = (HEIGHT/2)-(NAMES_SIZE/2)

        graphics.drawString(names, nameX, nameY)
    }

    private fun applyLiseLogo() {
        val width = logoImage.width / LOGO_SCALE_RATIO
        val height = logoImage.height / LOGO_SCALE_RATIO
        graphics.drawImage(logoImage, WIDTH-width-180, HEIGHT-height-50, width, height, null)
    }

    fun save(path: String) : Image {
        graphics.dispose()

        val pathFile = File(path)
        if(!pathFile.exists()) {
            pathFile.mkdir()
        }
        val imagePath = File(pathFile, "${person.firstName} ${person.lastName}.png")
        ImageIO.write(image, "png", imagePath)
        return image
    }
}