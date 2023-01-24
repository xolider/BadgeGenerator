package fr.liseconferences.badgegenerator

import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if(args.isEmpty()) {
        exitProcess(1)
    }
    if(args.size > 2) {
        exitProcess(1)
    }

    val reader = CSVReader(args[0], if(args.size == 2) args[1] == "repas" else false)

    reader.people.map { BadgeGenerator(it) }.forEach {
        it.save("badgeImages")
    }
}