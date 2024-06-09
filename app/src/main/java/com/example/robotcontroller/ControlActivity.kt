package com.example.robotcontroller

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.util.UUID
import kotlin.concurrent.thread

class ControlActivity : AppCompatActivity() {

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private var bluetoothSocket: BluetoothSocket? = null
    private lateinit var deviceAddress: String
    private val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private var keepSendingCommand = false
    private var currentCommand = ""
    private var lastPressedButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        deviceAddress = intent.getStringExtra(MainActivity.EXTRA_DEVICE_ADDRESS)!!
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        connectToDevice()
        setupButtons()
    }

    private fun connectToDevice() {
        val device: BluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceAddress)
        try {
            bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid)
            bluetoothSocket?.connect()
            Toast.makeText(this, "Połączono z $deviceAddress", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Połączenie nieudane", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendCommand(command: String) {
        currentCommand = command
        keepSendingCommand = true

        thread {
            while (keepSendingCommand) {
                if (bluetoothSocket?.isConnected == true) {
                    try {
                        bluetoothSocket?.outputStream?.write(currentCommand.toByteArray())
                        Thread.sleep(50) // Czas pomiędzy wysłaniem kolejnych komend
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun stopSendingCommand() {
        keepSendingCommand = false
    }

    private fun setupButtons() {
        setupForwardButton()
        setupBackwardButton()
        setupLeftButton()
        setupRightButton()
        setupDynamicLeftButton()
        setupDynamicRightButton()
        setupDynamicLeftBackButton()
        setupDynamicRightBackButton()
        setupStopButton()
        setupTowerLeftButton()
        setupTowerRightButton()
        setupTowerUpButton()
        setupTowerDownButton()
        setupTowerUpLeftButton()
        setupTowerUpRightButton()
        setupTowerDownLeftButton()
        setupTowerDownRightButton()
        setupTowerHomeButton()
        setupMode0Button()
        setupMode1Button()
        setupMode2Button()
        setupMode3Button()
        setupGear6Button()
        setupGear7Button()
        setupGear8Button()
        setupGear9Button()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupForwardButton() {
        val button: Button = findViewById(R.id.forwardButton)
        button.setOnClickListener {
            handleButtonClick(button, "w")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupBackwardButton() {
        val button: Button = findViewById(R.id.backwardButton)
        button.setOnClickListener {
            handleButtonClick(button, "x")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupLeftButton() {
        val button: Button = findViewById(R.id.leftButton)
        button.setOnClickListener {
            handleButtonClick(button, "a")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupRightButton() {
        val button: Button = findViewById(R.id.rightButton)
        button.setOnClickListener {
            handleButtonClick(button, "d")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupDynamicLeftButton() {
        val button: Button = findViewById(R.id.dynamicLeftButton)
        button.setOnClickListener {
            handleButtonClick(button, "q")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupDynamicRightButton() {
        val button: Button = findViewById(R.id.dynamicRightButton)
        button.setOnClickListener {
            handleButtonClick(button, "e")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupDynamicLeftBackButton() {
        val button: Button = findViewById(R.id.dynamicLeftBackButton)
        button.setOnClickListener {
            handleButtonClick(button, "z")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupDynamicRightBackButton() {
        val button: Button = findViewById(R.id.dynamicRightBackButton)
        button.setOnClickListener {
            handleButtonClick(button, "c")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupStopButton() {
        val button: Button = findViewById(R.id.stopButton)
        button.setOnClickListener {
            handleButtonClick(button, "s")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupTowerLeftButton() {
        val button: Button = findViewById(R.id.towerLeftButton)
        button.setOnClickListener {
            handleButtonClick(button, "g")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupTowerRightButton() {
        val button: Button = findViewById(R.id.towerRightButton)
        button.setOnClickListener {
            handleButtonClick(button, "j")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupTowerUpButton() {
        val button: Button = findViewById(R.id.towerUpButton)
        button.setOnClickListener {
            handleButtonClick(button, "y")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupTowerDownButton() {
        val button: Button = findViewById(R.id.towerDownButton)
        button.setOnClickListener {
            handleButtonClick(button, "n")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupTowerUpLeftButton() {
        val button: Button = findViewById(R.id.towerUpLeftButton)
        button.setOnClickListener {
            handleButtonClick(button, "t")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupTowerUpRightButton() {
        val button: Button = findViewById(R.id.towerUpRightButton)
        button.setOnClickListener {
            handleButtonClick(button, "u")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupTowerDownLeftButton() {
        val button: Button = findViewById(R.id.towerDownLeftButton)
        button.setOnClickListener {
            handleButtonClick(button, "b")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupTowerDownRightButton() {
        val button: Button = findViewById(R.id.towerDownRightButton)
        button.setOnClickListener {
            handleButtonClick(button, "m")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupTowerHomeButton() {
        val button: Button = findViewById(R.id.towerHomeButton)
        button.setOnClickListener {
            handleButtonClick(button, "h")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupMode0Button() {
        val button: Button = findViewById(R.id.mode0Button)
        button.setOnClickListener {
            handleButtonClick(button, "0")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupMode1Button() {
        val button: Button = findViewById(R.id.mode1Button)
        button.setOnClickListener {
            handleButtonClick(button, "1")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupMode2Button() {
        val button: Button = findViewById(R.id.mode2Button)
        button.setOnClickListener {
            handleButtonClick(button, "2")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupMode3Button() {
        val button: Button = findViewById(R.id.mode3Button)
        button.setOnClickListener {
            handleButtonClick(button, "3")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupGear6Button() {
        val button: Button = findViewById(R.id.gear6Button)
        button.setOnClickListener {
            handleButtonClick(button, "6")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupGear7Button() {
        val button: Button = findViewById(R.id.gear7Button)
        button.setOnClickListener {
            handleButtonClick(button, "7")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupGear8Button() {
        val button: Button = findViewById(R.id.gear8Button)
        button.setOnClickListener {
            handleButtonClick(button, "8")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupGear9Button() {
        val button: Button = findViewById(R.id.gear9Button)
        button.setOnClickListener {
            handleButtonClick(button, "9")
        }
    }

    private fun handleButtonClick(button: Button, command: String) {
        if (lastPressedButton == button) {
            stopSendingCommand()
            lastPressedButton = null
        } else {
            sendCommand(command)
            lastPressedButton = button
        }
    }
}
