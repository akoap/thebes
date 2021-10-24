package com.example.thebesmobile

import android.Manifest
import android.R
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.zxing.WriterException
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class CouponActivity : AppCompatActivity() {
    // variables for imageview, edittext,
    // button, bitmap and qrencoder.
    private var nameText: TextView? = null
    private var descriptionText: TextView? = null
    private var qrCodeIV: ImageView? = null
    private var dataEdt: EditText? = null
    private var generateQrBtn: Button? = null
    private var returnBtn: Button? = null
    private var extras: Bundle? = null
    var bitmap: Bitmap? = null
    var qrgEncoder: QRGEncoder? = null
    private var debugTag = "debug_nfc"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.thebesmobile.R.layout.activity_coupon)


        // initializing all variables.
        nameText = findViewById(com.example.thebesmobile.R.id.idName)
        descriptionText = findViewById(com.example.thebesmobile.R.id.idDescription)
        qrCodeIV = findViewById(com.example.thebesmobile.R.id.idIVQrcode)
        generateQrBtn = findViewById(com.example.thebesmobile.R.id.idBtnGenerateQR)
        returnBtn = findViewById(com.example.thebesmobile.R.id.idReturn)

        nameText?.setText(intent.extras?.getString("name")!!)
        descriptionText?.setText(intent.extras?.getString("description")!!)

        // can put a check here for valid nfc
        if (false) {


            // if the edittext inputs are empty then execute
            // this method showing a toast message.
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
            qrgEncoder = QRGEncoder(intent.extras?.getString("id"), null, QRGContents.Type.TEXT, dimen)
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

        val view = findViewById<ImageView>(com.example.thebesmobile.R.id.idIVQrcode)

        generateQrBtn?.setOnClickListener {
            // get the bitmap of the view using
            // getScreenShotFromView method it is
            // implemented below
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

            val bitmap = getScreenShotFromView(view)

            // if bitmap is not null then
            // save it to gallery
            if (bitmap != null) {
                saveMediaToStorage(bitmap)
            }
        }

        returnBtn?.setOnClickListener {
            val intent = Intent(this@CouponActivity, MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun getScreenShotFromView(v: View): Bitmap? {
        // create a bitmap object
        var screenshot: Bitmap? = null
        try {
            // inflate screenshot object
            // with Bitmap.createBitmap it
            // requires three parameters
            // width and height of the view and
            // the background color
            screenshot = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
            // Now draw this bitmap on a canvas
            val canvas = Canvas(screenshot)
            v.draw(canvas)
        } catch (e: Exception) {
            Log.e("GFG", "Failed to capture screenshot because:" + e.message)
        }
        // return the bitmap
        return screenshot
    }

    private fun saveMediaToStorage(bitmap: Bitmap) {
        // Generating a file name
        val filename = "${System.currentTimeMillis()}.jpg"

        // Output stream
        var fos: OutputStream? = null

        // For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // getting the contentResolver
            this.contentResolver?.also { resolver ->

                // Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    // putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                // Inserting the contentValues to
                // contentResolver and getting the Uri
                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                // Opening an outputstream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            // These for devices running on android < Q
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            // Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }

        generateQrBtn?.setText("Coupon Saved!")
    }

}