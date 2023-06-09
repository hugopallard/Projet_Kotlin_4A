package com.example.kotlinv2.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.kotlinv2.MovieDetailActivity
import com.example.kotlinv2.R
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import android.content.pm.ActivityInfo
import android.widget.ImageView

import com.google.zxing.qrcode.QRCodeReader

class ScanQRCodeFragment : Fragment() {

    private lateinit var root:View
    lateinit var qrCodeScanner: IntentIntegrator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        root = inflater.inflate(R.layout.fragment_scan_qr_code, container, false)

        qrCodeScanner = IntentIntegrator.forSupportFragment(this)
        qrCodeScanner.setOrientationLocked(false)
        checkCameraPermission()
        // Démarrez le scanner lorsque le bouton est cliqué
        val scanButton = root.findViewById<ImageView>(R.id.scanButton)
        scanButton.setOnClickListener {
            checkCameraPermission()
        }
        return root
    }
    private fun checkCameraPermission() {
        val permission = Manifest.permission.CAMERA
        if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permission)) {
                // Afficher une explication à l'utilisateur si nécessaire
                requestCameraPermission()
            }
        } else {
            startScanner()
        }
    }

    private fun requestCameraPermission() {
        val permission = Manifest.permission.CAMERA
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), CAMERA_PERMISSION_REQUEST_CODE)

        startScanner()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScanner()
            } else {
                Log.ERROR
            }
        }
    }

    private fun startScanner() {
        qrCodeScanner.setOrientationLocked(false)
        qrCodeScanner.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Récupérez les résultats du scanner de codes QR
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result.contents != null) {
            val scannedData = result.contents
            /* val movieIdRecup = scannedData.substringAfterLast("/movie/").substringBefore("-")*/
            val movieId = Integer.parseInt(scannedData)
            val intent = Intent(this@ScanQRCodeFragment.context, MovieDetailActivity::class.java)
            intent.putExtra("movieId",movieId)
            startActivity(intent)
        } else {
            Log.d("Scan","Le scan a échoué")
            // Le scan a été annulé ou n'a pas donné de résultats valides
        }

    }
    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 1001
    }

}