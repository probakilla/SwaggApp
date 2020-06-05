package com.pella.swaggapp

import com.pella.swaggapp.onepiece_scans.ScansFileManager
import org.junit.After
import org.junit.Assert
import org.junit.Test
import java.io.File

class ScanFileManagerTest {
    private val _filename = "test_file.json"

    @After
    fun tearDown() {
        File(_filename).delete()
    }

    @Test
    fun testInit() {
        ScansFileManager(_filename)
        Assert.assertTrue(File(_filename).exists())
    }

    @Test
    fun testGetChapters() {
        val manager = ScansFileManager(_filename)
        var testList = manager.getChapters()
        Assert.assertTrue(testList.isEmpty())

        manager.addChapter(1)
        testList = manager.getChapters()
        Assert.assertEquals(1, testList.size)
        Assert.assertTrue(testList.contains(1))

        manager.addChapter(2)
        testList = manager.getChapters()
        Assert.assertEquals(2, testList.size)
        Assert.assertTrue(testList.containsAll(listOf(1, 2)))

        manager.addChapter(69)
        testList = manager.getChapters()
        Assert.assertEquals(3, testList.size)
        Assert.assertTrue(testList.containsAll(listOf(1, 2, 69)))

        manager.deleteChapter(3)
        testList = manager.getChapters()
        Assert.assertEquals(3, testList.size)
        Assert.assertTrue(testList.containsAll(listOf(1, 2, 69)))

        manager.deleteChapter(2)
        testList = manager.getChapters()
        Assert.assertEquals(2, testList.size)
        Assert.assertTrue(testList.containsAll(listOf(1, 69)))

        manager.addChapter(69)
        testList = manager.getChapters()
        Assert.assertEquals(2, testList.size)
        Assert.assertTrue(testList.containsAll(listOf(1, 69)))
    }
}