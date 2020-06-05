package com.pella.swaggapp

import com.pella.swaggapp.onepiece_scans.InfoFetcher
import org.junit.Assert
import org.junit.Test

class InfoFetcherTest {
    @Test
    fun testGetLastChapters() {
        val fetcher = InfoFetcher()
        val testList = fetcher.getLastChapters()
        Assert.assertEquals(20, testList.size)
        for (i in 1 until testList.size) {
            Assert.assertTrue(testList[i] < testList[i - 1])
        }
    }
}