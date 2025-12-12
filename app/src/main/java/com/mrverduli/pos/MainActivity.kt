package com.mrverduli.pos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.mrverduli.pos.ui.theme.MrVerduliPOSTheme
import com.mrverduli.pos.ui.POSScreen
import com.mrverduli.pos.usb.USBScaleManager

class MainActivity : ComponentActivity() {
    
    private lateinit var usbManager: USBScaleManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inicializar gestor USB
        usbManager = USBScaleManager(this)
        usbManager.scanDevices()
        
        setContent {
            MrVerduliPOSTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    POSScreen(usbManager = usbManager)
                }
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        usbManager.scanDevices()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        usbManager.cleanup()
    }
}
