package com.pella.swaggapp.onepiece_scans

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.io.File

@Serializable
data class Scans(var scanList: MutableList<Int> = mutableListOf())

class ScansFileManager(filename: String) {
    private val _filename: String = filename
    private val _json: Json = Json(JsonConfiguration.Stable)

    init {
        File(_filename).createNewFile()
        if (isFileEmpty()) {
            saveScans(Scans())
        }
    }

    private fun isFileEmpty(): Boolean {
        val file = File(_filename)
        if (file.isFile) {
            val size = file.length()
            return size == 0L
        }
        return true
    }

    private fun saveScans(scans: Scans) {
        val jsonData = _json.stringify(Scans.serializer(), scans)
        File(_filename).writeText(jsonData)
    }

    fun addChapter(chapter: Int) {
        val scans = getFileContent()
        if (!scans.scanList.contains(chapter)) {
            scans.scanList.add(chapter)
            saveScans(scans)
        }
    }

    fun getChapters(): MutableList<Int> {
        val scans = getFileContent()
        return scans.scanList
    }

    private fun getFileContent(): Scans {
        val content = File(_filename).readText()
        return _json.parse(Scans.serializer(), content)
    }

    fun deleteChapter(chapter: Int) {
        val scans = getFileContent()
        scans.scanList.remove(chapter)
        saveScans(scans)
    }
}