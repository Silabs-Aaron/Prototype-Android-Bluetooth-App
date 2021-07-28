package com.example.prototypebluetoothapp

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.nfc.Tag
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.service.controls.actions.ControlAction
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prototypebluetoothapp.databinding.ActivityMainBinding
import com.example.prototypebluetoothapp.databinding.RecyclerviewDeviceBinding.inflate
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var manager: RecyclerView.LayoutManager
    val recyclerView = findViewById<RecyclerView>(R.id.RecyclerView)

    private val scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        .build()

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            Log.v("MainActivity", activityResult.toString())
        }

    private lateinit var bAdapter: BluetoothAdapter
    private lateinit var m_pairedDevices: Set<BluetoothDevice>

    private val ENABLE_BLUETOOTH_REQUEST_CODE = 1

    private val isLocationPermissionGranted: Boolean
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }

        }

    // Callback for the Devices after the Scan button is pushed
    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            with(result.device) {
                Log.v("Callback",
                    "BLUETOOTH DEVICES FOUND! Name: ${name ?: "Unnamed"}, address: $address"
                )
            }
        }
    }


    //Permission Requests for what the Results are from the Activity
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {

            } else {

            }
        }

    //Start Bluetooth Scan function
    private fun startBleScan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !isLocationPermissionGranted) {
            requestPermission.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            this.bAdapter.bluetoothLeScanner.startScan(null, scanSettings, scanCallback)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bAdapter = BluetoothAdapter.getDefaultAdapter()

        val bluetoothTv = findViewById<TextView>(R.id.RecyclerView)
        val scanBtn = findViewById<Button>(R.id.scanBtn)

        scanBtn.setOnClickListener {
            startBleScan()

        }

        var data = listOf(
            DataModel(1, "first"),
            DataModel(2, "Second"),
            DataModel(3, "Third"),
            DataModel(4, "Fourth"),
            DataModel(5, "Fifth"),
            DataModel(6, "sixth")
        )

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        manager = LinearLayoutManager(this)

        binding.RecyclerView.apply{
            Adpater = RecyclerViewAdapter(data)
            layoutManager = manager

        }

    }
}


