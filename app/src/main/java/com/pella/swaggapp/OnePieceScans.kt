package com.pella.swaggapp

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_one_piece_scans.*
import okhttp3.*
import java.io.IOException

class OnePieceScans : AppCompatActivity() {

    private var scanNumber: String? = ""
    private var imageNumber: Int = 0
    private val BASE_URL: String = "http://lelscano.com/mangas/one-piece/"

    private val okClient by lazy {
        OkHttpClient()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_piece_scans)
        initActivity()
        loadImage()
        assignButtons()
    }

    private fun assignButtons() {
        nextBtn.setOnClickListener {
            nextImage()
        }
        prevBtn.setOnClickListener {
            previousImage()
        }
    }

    private fun initActivity() {
        scanNumber = intent.getStringExtra("scannumber")
        imageNumber = intent.getIntExtra("imagenumber", 0)
        updateDisplay()
    }

    private fun updateDisplay() {
        scanNumberText.text = scanNumber
        imageNumberText.text = imageNumber.toString()
    }

    private fun getScanUrl(): String {
        return "$BASE_URL$scanNumber/${convertImageNumber()}.jpg"
    }

    private fun convertImageNumber(): String {
        return if (imageNumber < 10) "0$imageNumber" else "$imageNumber"
    }

    private fun loadImage() {
        cancelRequests()
        val url = getScanUrl()
        val okRequest: Request = Request.Builder().url(url).build()
        okClient.newCall(okRequest).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val inputStream = response.body?.byteStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)
                runOnUiThread {
                    scanContainer.setImageBitmap(bitmap)
                }
            }
        })
    }

    private fun cancelRequests() {
        for (call in okClient.dispatcher.runningCalls()) {
            call.cancel()
        }
    }

    private fun nextImage() {
        imageNumber += 1
        updateDisplay()
        loadImage()
    }

    private fun previousImage() {
        if (imageNumber > 0) {
            imageNumber -= 1
            updateDisplay()
            loadImage()
        }
    }

    companion object {
        fun getScanIntent(context: Context, scanNum: String, imageNum: Int): Intent {
            val intent = Intent(context, OnePieceScans::class.java)
            intent.putExtra("scannumber", scanNum)
            intent.putExtra("inagemunber", imageNum)
            return intent
        }
    }
}
