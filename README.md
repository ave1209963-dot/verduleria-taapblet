# App Android POS con Báscula USB OTG

## Descripción
Aplicación Android nativa para punto de venta con lectura directa de báscula Torrey PCR-40 via USB OTG.

## Características
- ✅ Lectura USB directa (sin navegador, sin limitaciones)
- ✅ Soporte USB OTG nativo de Android
- ✅ Interfaz POS completa
- ✅ Sincronización con servidor vaes.digital
- ✅ Funciona offline con SQLite local
- ✅ Driver nativo para Torrey PCR-40

## Tecnologías
- **Kotlin** - Lenguaje moderno para Android
- **Jetpack Compose** - UI moderna y reactiva
- **USB Serial** - Comunicación USB/OTG
- **Retrofit** - Sincronización con servidor
- **Room** - Base de datos local SQLite

## Requisitos
- Android 9.0+ (API 28+)
- Cable USB OTG
- Tablet/Dispositivo con soporte OTG
- Android Studio (para compilar)

## Instalación Rápida
1. Instala el APK en tu tablet: `app-release.apk`
2. Conecta báscula con cable USB OTG
3. Abre la app y otorga permisos USB
4. Configura servidor: `https://vaes.digital`
5. ¡Listo para vender!

## Compilación
```bash
# Abre el proyecto en Android Studio
# Build > Generate Signed Bundle / APK
# Selecciona APK y firma con tu keystore
```

## Estructura del Proyecto
```
app/
├── src/main/
│   ├── java/com/mrverduli/pos/
│   │   ├── MainActivity.kt          # Actividad principal
│   │   ├── ui/
│   │   │   ├── POSScreen.kt         # Pantalla POS
│   │   │   ├── ProductGrid.kt       # Grid de productos
│   │   │   └── CartPanel.kt         # Panel de carrito
│   │   ├── usb/
│   │   │   ├── TorreyScaleDriver.kt # Driver báscula Torrey
│   │   │   └── USBManager.kt        # Gestor USB OTG
│   │   ├── data/
│   │   │   ├── database/            # Room DB local
│   │   │   ├── models/              # Modelos de datos
│   │   │   └── repository/          # Repositorios
│   │   └── network/
│   │       └── ApiService.kt        # Retrofit API
│   ├── res/
│   │   ├── layout/                  # Layouts XML (fallback)
│   │   ├── values/                  # Strings, colors, themes
│   │   └── xml/
│   │       └── device_filter.xml    # Filtro USB
│   └── AndroidManifest.xml          # Manifest con permisos USB
└── build.gradle                     # Dependencias
```

## Configuración USB
El manifest incluye:
- Permiso `MANAGE_EXTERNAL_STORAGE`
- Permiso `USB_PERMISSION`
- Intent filter para conexión automática USB
- Soporte VID:PID Torrey (0483:5740)

## Uso
1. **Primera vez:**
   - Conecta báscula USB
   - Android mostrará diálogo de permisos
   - Acepta "Siempre usar esta app"

2. **Operación:**
   - Peso se actualiza automáticamente
   - Click en producto → agrega al carrito
   - Botón "Báscula" → modal con peso en vivo
   - Botón "Cobrar" → procesar venta

3. **Sincronización:**
   - Online: Sincroniza con vaes.digital
   - Offline: Guarda local, sincroniza después

## Ventajas vs Web
| Característica | Web (Chrome) | App Nativa |
|----------------|--------------|------------|
| USB OTG | ❌ No soportado | ✅ Soporte completo |
| Velocidad | ~500ms | ~50ms |
| Offline | ❌ No | ✅ Sí |
| Permisos | Limitados | ✅ Completos |
| Estabilidad | Depende navegador | ✅ Alta |

## Próximos pasos
1. Compila con Android Studio
2. Genera APK firmado
3. Instala en tablet
4. ¡A vender!
