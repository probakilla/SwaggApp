package com.pella.swaggapp.onepiece_scans

import java.io.File
import java.io.FileWriter
import java.io.IOException

class ScansFileManager(filename: String) {
    private val _filename: String = filename

    init {
        File(_filename).createNewFile()
    }

    fun addChapter(chapter: Int) {
        if (!isAlreadyRegistered(chapter)) {
            val path = _filename
            val text = "$chapter${System.lineSeparator()}"
            try {
                val fileWriter = FileWriter(path, true)
                fileWriter.write(text)
                fileWriter.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun getChapters(): MutableList<Int> {
        val content = File(_filename).readText()
        val list: List<String> = content.split(System.lineSeparator()).map { it.trim() }
        val scanList = list.filter { it != "" }
        val chapters = scanList.map { el -> el.toInt() }
        return chapters.sortedDescending().toMutableList()
    }

    private fun isAlreadyRegistered(chapter: Int): Boolean {
        val content = File(_filename).readText()
        val regex = "^$chapter".toRegex()
        return (content.contains(regex))
    }

    fun deleteChapter(chapter: Int) {
        val content = File(_filename).readText()
        val newContent = content.replace("$chapter${System.lineSeparator()}", "")
        File(_filename).writeText(newContent)
        cleanContent()
    }

    private fun cleanContent() {
        val content = File(_filename).readText()
        val chaptersListStr = content.split(System.lineSeparator()).map { it.trim() }
        val filteredList = chaptersListStr.filter { it != "" }
        val cleanedContent = filteredList.joinToString(System.lineSeparator())
        File(_filename).writeText(cleanedContent)
    }

    fun printFile() {
        File(_filename).forEachLine { println(it) }
    }
}