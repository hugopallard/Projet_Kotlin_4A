package com.example.kotlinv2

import android.content.pm.PackageManager
import android.os.Bundle
import android.Manifest
import android.view.SurfaceHolder
import android.view.TextureView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.kotlinv2.fragments.ScanQRCodeFragment
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.journeyapps.barcodescanner.BarcodeResult

class ScanQRCodeActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_scan_qr_code)

        val scannerFragment = ScanQRCodeFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.scanContainer, scannerFragment)
            .commit()
    }




}