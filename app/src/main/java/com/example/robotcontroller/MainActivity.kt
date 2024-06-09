package com.example.robotcontroller

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult

class MainActivity : AppCompatActivity() {

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var deviceList: ListView
    private lateinit var pairedDevices: Set<BluetoothDevice>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        deviceList = findViewById(R.id.deviceList)
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth niedostępny", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, 1)
        }

        displayPairedDevices()
    }

    private fun displayPairedDevices() {
        pairedDevices = bluetoothAdapter.bondedDevices
        val list: ArrayList<String> = ArrayList()

        if (pairedDevices.isNotEmpty()) {
            for (device in pairedDevices) {
                list.add("${device.name}\n${device.address}")
            }
        } else {
            list.add("Nie znaleziono zparowanych urzadzen")
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        deviceList.adapter = adapter
        deviceList.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val deviceInfo = list[position]
            if (deviceInfo != "Nie jesteś połączonay z żadnym urządzeniem") {
                val address = deviceInfo.substring(deviceInfo.length - 17)
                val intent = Intent(this, ControlActivity::class.java)
                intent.putExtra(EXTRA_DEVICE_ADDRESS, address)
                startActivity(intent)
            }
        }
    }

    companion object {
        const val EXTRA_DEVICE_ADDRESS = "device_address"
    }
}
