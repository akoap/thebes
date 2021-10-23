package com.example.thebesmobile

import android.R
import android.graphics.Bitmap
import android.graphics.Point
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.WriterException

class CouponActivity : AppCompatActivity() {
    // variables for imageview, edittext,
    // button, bitmap and qrencoder.
    private var nameText: TextView? = null
    private var descriptionText: TextView? = null
    private var qrCodeIV: ImageView? = null
    private var dataEdt: EditText? = null
    private var generateQrBtn: Button? = null
    var bitmap: Bitmap? = null
    var qrgEncoder: QRGEncoder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.thebesmobile.R.layout.activity_coupon)

        // initializing all variables.
        nameText = findViewById(com.example.thebesmobile.R.id.idName)
        descriptionText = findViewById(com.example.thebesmobile.R.id.idDescription)
        qrCodeIV = findViewById(com.example.thebesmobile.R.id.idIVQrcode)
        generateQrBtn = findViewById(com.example.thebesmobile.R.id.idBtnGenerateQR)

        nameText?.setText("West Village - Free Raw Chicken")
        descriptionText?.setText("Head to West Village by the end of the week to die")

        // initializing onclick listener for button.
        if (false) {

            // if the edittext inputs are empty then execute
            // this method showing a toast message.
            Toast.makeText(this@CouponActivity, "Enter some text to generate QR Code", Toast.LENGTH_SHORT).show()
        } else {
            // below line is for getting
            // the windowmanager service.
            val manager = getSystemService(WINDOW_SERVICE) as WindowManager

            // initializing a variable for default display.
            val display = manager.defaultDisplay

            // creating a variable for point which
            // is to be displayed in QR Code.
            val point = Point()
            display.getSize(point)

            // getting width and
            // height of a point
            val width = point.x
            val height = point.y

            // generating dimension from width and height.
            var dimen = if (width < height) width else height
            dimen = dimen * 3 / 4

            // setting this dimensions inside our qr code
            // encoder to generate our qr code.
            qrgEncoder = QRGEncoder("3843847548392", null, QRGContents.Type.TEXT, dimen)
            try {
                // getting our qrcode in the form of bitmap.
                bitmap = qrgEncoder!!.encodeAsBitmap()
                // the bitmap is set inside our image
                // view using .setimagebitmap method.
                qrCodeIV?.setImageBitmap(bitmap)
            } catch (e: WriterException) {
                // this method is called for
                // exception handling.
                Log.e("Tag", e.toString())
            }
        }
    }
}