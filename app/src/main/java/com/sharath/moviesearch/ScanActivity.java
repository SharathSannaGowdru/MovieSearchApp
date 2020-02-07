package com.sharath.moviesearch;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends Activity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    public static String scan_result="";

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);// Set the scanner view as the content view
        scan_result="";

    }
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();

        Log.e("ScanActivity","hasFlash() ==> "+hasFlash());
        if(hasFlash()){
            mScannerView.setFlash(false);
        }else{
        }
        // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
        // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.e("ScanActivity","rawResult.getText() ==> "+rawResult.getText()); // Prints scan results
        Log.e("ScanActivity", "getBarcodeFormat() ==> "+rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)


        scan_result = rawResult.getText();

        if(scan_result.contains("http://omdbapi.com/")){

            String URL = scan_result;
            int index = URL.indexOf("i=");
            String Result = URL.substring(index+2);
            String imdb_id = Result.substring(0, 9);

            System.out.println("------------------------------------------------------------------"+imdb_id);

            Intent intent = new Intent(ScanActivity.this,MovieDetailesActivity.class);
            intent.putExtra("imdbID",imdb_id);
            startActivity(intent);

        }else{

            Toast.makeText(getApplicationContext(), "Invalid QR Code!!!", Toast.LENGTH_SHORT).show();

        }
        // If you would like to resume scanning, call this method below:
       // mScannerView.resumeCameraPreview(this);
    }

    private boolean hasFlash() {
        return getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }



}