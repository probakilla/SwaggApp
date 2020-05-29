package com.pella.swaggapp

import android.os.Bundle
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.pella.swaggapp.onepiece_scans.ScansFileManager
import kotlinx.android.synthetic.main.activity_scans_manager.*

class ScansManagerActivity : AppCompatActivity() {
    private lateinit var _fileManager: ScansFileManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scans_manager)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        initFileManager()
        bindButton()
        initList(_fileManager.getChapters())
    }

    private fun initFileManager() {
        val filename = filesDir.path.toString() + "/scans.txt"
        _fileManager = ScansFileManager(filename)
    }

    private fun bindButton() {
        scanBtn.setOnClickListener {
            addChapter()
            initList(_fileManager.getChapters())
        }
    }

    private fun addChapter() {
        val chapter = scanInput.text.toString()
        _fileManager.addChapter(chapter.toInt())
        val intent = ScanReadingActivity.getScanIntent(this, chapter)
        startActivity(intent)
    }

    private fun initList(chapterList: MutableList<Int>) {
        val adapter = ArrayAdapter(this, R.layout.scan_list_view, chapterList)
        scanListView.adapter = adapter
        scanListView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val itemValue = scanListView.getItemAtPosition(position) as Int
                val builder = AlertDialog.Builder(this@ScansManagerActivity)
                builder.setMessage("Delete $itemValue ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { _, _ ->
                        _fileManager.deleteChapter(itemValue)
                        chapterList.remove(itemValue)
                        adapter.notifyDataSetChanged()
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                builder.create().show()
            }
    }
}
