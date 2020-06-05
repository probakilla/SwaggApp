package com.pella.swaggapp.onepiece_scans

import org.jsoup.Jsoup

class InfoFetcher {
    private val URL: String = "http://lelscano.com/lecture-ligne-one-piece.php"
    private val MAX = 20

    fun getLastChapters(): List<Int> {
        val doc = Jsoup.connect(URL).get()
        val chapterSelect = doc.select("select").first()
        val options = chapterSelect.select("option").take(MAX)
        val ret = options.map { element -> element.text().toInt() }
        return ret
    }
}