package com.erwintobing15.siakup.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.erwintobing15.siakup.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

public class PermissionActivity extends AppCompatActivity implements View.OnClickListener{

    CardView cv_bluetooth;
    CardView cv_wifi;
    CardView cv_kamera;
    CardView cv_gps;
    CardView cv_contact;
    CardView cv_soundrec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        initViews();
        initListener();
    }

    private void initViews() {
        cv_bluetooth = findViewById(R.id.cv_bluetooth);
        cv_wifi = findViewById(R.id.cv_wifi);
        cv_kamera = findViewById(R.id.cv_kamera);
        cv_gps = findViewById(R.id.cv_gps);
        cv_contact = findViewById(R.id.cv_contact);
        cv_soundrec = findViewById(R.id.cv_soundrec);
    }

    private void initListener() {
        cv_bluetooth.setOnClickListener(this);
        cv_wifi.setOnClickListener(this);
        cv_kamera.setOnClickListener(this);
        cv_gps.setOnClickListener(this);
        cv_contact.setOnClickListener(this);
        cv_soundrec.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.cv_bluetooth:
                grantBluetooth();
                break;

            case R.id.cv_wifi:
                grantWifi();
                break;

            case R.id.cv_kamera:
                grantCamera();
                break;

            case R.id.cv_gps:
                grantGps();
                break;

            case R.id.cv_contact:
                grantContact();
                break;

            case R.id.cv_soundrec:
                grantRecorder();
                break;

        }
    }

    private void grantBluetooth() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "Bluetooth Granted !", Toast.LENGTH_SHORT).show();
                            toggleBluetooth();
                        }
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            openSettings();
                        }

                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }
                }).check();
    }

    private void toggleBluetooth(){
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if(adapter != null) {
            if(adapter.getState() == BluetoothAdapter.STATE_ON) {
                adapter.disable();
            } else if (adapter.getState() == BluetoothAdapter.STATE_OFF){
                adapter.enable();
            } else {
                //State.INTERMEDIATE_STATE;
            }
        }
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void grantWifi(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.CHANGE_WIFI_STATE
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if(report.areAllPermissionsGranted()){
                            Toast.makeText(getApplicationContext(), "Bluetooth Granted !", Toast.LENGTH_SHORT).show();
                            toggleWifi();
                        }
                        if(report.isAnyPermissionPermanentlyDenied()){
                            Toast.makeText(getApplicationContext(), "Permission Denied, change your permission in settings !", Toast.LENGTH_SHORT).show();
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }
                }).check();
    }
    private void toggleWifi(){
        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(wifi.isWifiEnabled()) {
            wifi.setWifiEnabled(false);
        }else {
            wifi.setWifiEnabled(true);
        }
    }

    private void grantCamera() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Toast.makeText(PermissionActivity.this, "Permission Granted, Thankyou !", Toast.LENGTH_SHORT).show();
                        openCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(PermissionActivity.this, "Denied !", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }

    private void grantGps(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if(report.areAllPermissionsGranted()){
                            Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
                            intent.putExtra("enabled", true);
                            sendBroadcast(intent);
                            Toast.makeText(getApplicationContext(), "GPS Granted!", Toast.LENGTH_SHORT).show();
                        }
                        if(report.isAnyPermissionPermanentlyDenied()){
                            Toast.makeText(getApplicationContext(), "GPS Already Denied Before, Please enable on settings", Toast.LENGTH_SHORT).show();
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }
                }).check();
    }

    private void grantRecorder(){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.RECORD_AUDIO)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Toast.makeText(PermissionActivity.this, "Permission Granted, Thankyou !", Toast.LENGTH_SHORT).show();
                        openCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(PermissionActivity.this, "Denied !", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void grantContact(){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_CONTACTS)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Toast.makeText(PermissionActivity.this, "Permission Granted, Thankyou !", Toast.LENGTH_SHORT).show();
                        openCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(PermissionActivity.this, "Denied !", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }
}