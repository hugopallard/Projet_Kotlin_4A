package com.example.kotlinv2

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

class QRCodeGenerate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_qrcode)

        val receivedData = intent.getStringExtra("key")
        val barcodeEncoder = BarcodeEncoder()
        try {
//            val bitmap: Bitmap = barcodeEncoder.encodeBitmap(receivedData, BarcodeFormat.QR_CODE, 400, 400)
//            qrCodeImageView.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }


    }
}