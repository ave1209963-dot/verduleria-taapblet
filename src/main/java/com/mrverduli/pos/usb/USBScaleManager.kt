package com.mrverduli.pos.usb

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.util.Log
import com.hoho.android.usbserial.driver.UsbSerialDriver
import com.hoho.android.usbserial.driver.UsbSerialProber
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Gestor USB para manejo de báscula OTG
 */
class USBScaleManager(private val context: Context) {
    
    private val TAG = "USBScaleManager"
    private val usbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
    
    private val _scaleDriver = MutableStateFlow<TorreyScaleDriver?>(null)
    val scaleDriver: StateFlow<TorreyScaleDriver?> = _scaleDriver.asStateFlow()
    
    private val _availableDevices = MutableStateFlow<List<UsbDevice>>(emptyList())
    val availableDevices: StateFlow<List<UsbDevice>> = _availableDevices.asStateFlow()
    
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var readJob: Job? = null
    
    companion object {
        const val ACTION_USB_PERMISSION = "com.mrverduli.pos.USB_PERMISSION"
    }
    
    /**
     * Escanea dispositivos USB conectados
     */
    fun scanDevices() {
        val devices = usbManager.deviceList.values.filter { device ->
            TorreyScaleDriver.isCompatible(device)
        }
        _availableDevices.value = devices
        Log.i(TAG, "Dispositivos compatibles encontrados: ${devices.size}")
    }
    
    /**
     * Solicita permiso para dispositivo USB
     */
    fun requestPermission(device: UsbDevice) {
        val permissionIntent = PendingIntent.getBroadcast(
            context,
            0,
            Intent(ACTION_USB_PERMISSION),
            PendingIntent.FLAG_MUTABLE
        )
        usbManager.requestPermission(device, permissionIntent)
    }
    
    /**
     * Conecta a báscula
     */
    fun connect(device: UsbDevice) {
        try {
            if (!usbManager.hasPermission(device)) {
                requestPermission(device)
                return
            }
            
            val availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(usbManager)
            if (availableDrivers.isEmpty()) {
                Log.e(TAG, "No se encontraron drivers USB")
                return
            }
            
            val driver: UsbSerialDriver = availableDrivers.firstOrNull { 
                it.device.deviceId == device.deviceId 
            } ?: return
            
            val connection = usbManager.openDevice(driver.device)
            if (connection == null) {
                Log.e(TAG, "No se pudo abrir conexión USB")
                return
            }
            
            val port = driver.ports.firstOrNull()
            if (port == null) {
                Log.e(TAG, "No hay puertos disponibles")
                connection.close()
                return
            }
            
            val torreyDriver = TorreyScaleDriver(port)
            torreyDriver.connect()
            _scaleDriver.value = torreyDriver
            
            // Iniciar lectura continua
            startContinuousRead()
            
            Log.i(TAG, "Báscula conectada: ${device.deviceName}")
            
        } catch (e: Exception) {
            Log.e(TAG, "Error conectando báscula", e)
        }
    }
    
    /**
     * Desconecta báscula
     */
    fun disconnect() {
        readJob?.cancel()
        _scaleDriver.value?.disconnect()
        _scaleDriver.value = null
        Log.i(TAG, "Báscula desconectada")
    }
    
    /**
     * Lectura continua cada 50ms (20 veces por segundo)
     */
    private fun startContinuousRead() {
        readJob?.cancel()
        readJob = scope.launch {
            while (isActive) {
                _scaleDriver.value?.requestWeight()
                delay(TorreyScaleDriver.READ_INTERVAL_MS)
            }
        }
    }
    
    /**
     * Obtiene peso actual
     */
    fun getCurrentWeight(): StateFlow<Double> {
        return _scaleDriver.value?.weightFlow 
            ?: MutableStateFlow(0.0).asStateFlow()
    }
    
    /**
     * Limpieza de recursos
     */
    fun cleanup() {
        disconnect()
        scope.cancel()
    }
}
