package fr.liseconferences.badgegenerator

import java.io.File
import java.io.FileNotFoundException

class CSVReader(path: String, val eat: Boolean) {

    private val csvFile = File(path)
    private val csvLines = csvFile.readLines()

    val people = mutableListOf<Person>()

    init {
        if(!csvFile.exists()) {
            throw FileNotFoundException(path)
        }
        people.addAll(csvLines.map {
            val split = it.split(',')
            Person(split[0], split[1], if(split.size == 3) split[2] else null, eat)
        })
    }

}