package com.mrverduli.pos.usb

import android.hardware.usb.UsbDevice
import android.util.Log
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.util.SerialInputOutputManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.nio.charset.StandardCharsets

/**
 * Driver para báscula Torrey PCR-40
 * Protocolo: ASCII, comando 'W', respuesta "  X.XXX kg\r"
 * Baudrate: 2400 bps
 */
class TorreyScaleDriver(
    private val port: UsbSerialPort
) : SerialInputOutputManager.Listener {
    
    private val TAG = "TorreyScale"
    
    private val _weightFlow = MutableStateFlow(0.0)
    val weightFlow: StateFlow<Double> = _weightFlow.asStateFlow()
    
    private val _connectionState = MutableStateFlow(ConnectionState.DISCONNECTED)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()
    
    private var ioManager: SerialInputOutputManager? = null
    private val buffer = StringBuilder()
    
    companion object {
        const val BAUDRATE = 2400
        const val DATA_BITS = 8
        const val STOP_BITS = UsbSerialPort.STOPBITS_1
        const val PARITY = UsbSerialPort.PARITY_NONE
        const val READ_INTERVAL_MS = 50L // 20 lecturas por segundo
        
        fun isCompatible(device: UsbDevice): Boolean {
            // Torrey PCR-40 usa chip STM32 Virtual COM Port
            return device.vendorId == 0x0483 && device.productId == 0x5740
        }
    }
    
    enum class ConnectionState {
        DISCONNECTED,
        CONNECTING,
        CONNECTED,
        ERROR
    }
    
    fun connect() {
        try {
            _connectionState.value = ConnectionState.CONNECTING
            
            port.open(null)
            port.setParameters(BAUDRATE, DATA_BITS, STOP_BITS, PARITY)
            port.dtr = true
            port.rts = true
            
            ioManager = SerialInputOutputManager(port, this).apply {
                start()
            }
            
            _connectionState.value = ConnectionState.CONNECTED
            Log.i(TAG, "Báscula conectada exitosamente")
            
        } catch (e: Exception) {
            _connectionState.value = ConnectionState.ERROR
            Log.e(TAG, "Error conectando báscula", e)
        }
    }
    
    fun disconnect() {
        try {
            ioManager?.stop()
            ioManager = null
            port.close()
            _connectionState.value = ConnectionState.DISCONNECTED
            Log.i(TAG, "Báscula desconectada")
        } catch (e: Exception) {
            Log.e(TAG, "Error desconectando báscula", e)
        }
    }
    
    fun requestWeight() {
        try {
            if (_connectionState.value == ConnectionState.CONNECTED) {
                port.write("W".toByteArray(StandardCharsets.US_ASCII), 100)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error enviando comando 'W'", e)
        }
    }
    
    override fun onNewData(data: ByteArray) {
        try {
            val text = String(data, StandardCharsets.US_ASCII)
            buffer.append(text)
            
            // Buscar fin de línea (CR)
            val crIndex = buffer.indexOf('\r')
            if (crIndex >= 0) {
                val line = buffer.substring(0, crIndex).trim()
                buffer.delete(0, crIndex + 1)
                
                // Parsear peso: "  X.XXX kg" o "  XX.XXX kg"
                parseWeight(line)
            }
            
            // Limitar buffer para evitar crecimiento infinito
            if (buffer.length > 100) {
                buffer.clear()
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "Error procesando datos", e)
        }
    }
    
    private fun parseWeight(line: String) {
        try {
            // Regex para extraer peso: busca número decimal seguido de "kg"
            val regex = Regex("""(\d+\.?\d*)\s*kg""", RegexOption.IGNORE_CASE)
            val match = regex.find(line)
            
            if (match != null) {
                val weight = match.groupValues[1].toDoubleOrNull()
                if (weight != null && weight >= 0.0) {
                    _weightFlow.value = weight
                    Log.d(TAG, "Peso: $weight kg")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error parseando peso: $line", e)
        }
    }
    
    override fun onRunError(e: Exception) {
        _connectionState.value = ConnectionState.ERROR
        Log.e(TAG, "Error en comunicación serial", e)
    }
}
