package fr.liseconferences.badgegenerator

import java.awt.Image
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if(args.isEmpty()) {
        exitProcess(1)
    }
    if(args.size > 2) {
        exitProcess(1)
    }

    val imgs = mutableListOf<Image>()

    val reader = CSVReader(args[0], if(args.size == 2) args[1] == "repas" else false)

    reader.people.map { BadgeGenerator(it) }.forEach {
        val img = it.save("badgeImages")
        imgs.add(img)
    }

    val merger = BadgeMerger(3, 3, imgs.toTypedArray(), "badgeImages")
    merger.merge()
}