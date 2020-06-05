package com.pella.swaggapp

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_one_piece_scans.*
import okhttp3.*
import java.io.IOException

class ScanReadingActivity : AppCompatActivity() {

    private var _scanNumber: String? = ""
    private var _imageNumber: Int = 0
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
        assignEditTexts()
    }

    private fun assignButtons() {
        nextBtn.setOnClickListener {
            nextImage()
        }
        prevBtn.setOnClickListener {
            previousImage()
        }
    }

    private fun assignEditTexts() {
        imageNumberText.setOnKeyListener(View.OnKeyListener { v, keycode, event ->
            if (keycode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                if (currentFocus != null) {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                }
                val chapter = imageNumberText.text.toString().toInt()
                setImage(chapter)
                return@OnKeyListener true
            }
            false
        })
    }

    private fun initActivity() {
        _scanNumber = intent.getStringExtra("scannumber")
        _imageNumber = intent.getIntExtra("imagenumber", 0)
        updateDisplay()
    }

    private fun updateDisplay() {
        scanNumberText.text = _scanNumber
        imageNumberText.text = _imageNumber.toEditable()
    }

    private fun Int.toEditable(): Editable =
        Editable.Factory.getInstance().newEditable(this.toString())

    private fun getScanUrl(): String {
        return "$BASE_URL$_scanNumber/${convertImageNumber()}.jpg"
    }

    private fun convertImageNumber(): String {
        return if (_imageNumber < 10) "0$_imageNumber" else "$_imageNumber"
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
        _imageNumber += 1
        updateDisplay()
        loadImage()
    }

    private fun previousImage() {
        if (_imageNumber > 0) {
            _imageNumber -= 1
            updateDisplay()
            loadImage()
        }
    }

    private fun setImage(number: Int) {
        _imageNumber = number
        updateDisplay()
        loadImage()
    }

    companion object {
        fun getScanIntent(context: Context, scanNum: String, imageNum: Int = 0): Intent {
            val intent = Intent(context, ScanReadingActivity::class.java)
            intent.putExtra("scannumber", scanNum)
            intent.putExtra("imagenumber", imageNum)
            return intent
        }
    }
}
