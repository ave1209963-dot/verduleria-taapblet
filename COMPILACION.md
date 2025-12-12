# GUÍA DE COMPILACIÓN - App Android Mr Verduli POS

## Opción 1: Android Studio (Recomendado)

### Requisitos:
- Android Studio Hedgehog (2023.1.1) o superior
- JDK 17
- Android SDK 34

### Pasos:

1. **Abrir proyecto:**
   ```
   File > Open > Seleccionar carpeta app-android
   ```

2. **Esperar sincronización de Gradle:**
   - Android Studio descargará dependencias automáticamente
   - Puede tardar 5-10 minutos la primera vez

3. **Compilar APK:**
   ```
   Build > Build Bundle(s) / APK(s) > Build APK(s)
   ```
   
4. **APK generado en:**
   ```
   app-android\app\build\outputs\apk\debug\app-debug.apk
   ```

5. **Generar APK Release (firmado):**
   ```
   Build > Generate Signed Bundle / APK
   > APK
   > Create new keystore
   > Completar datos de firma
   > Build
   ```

## Opción 2: Línea de Comandos (Gradle)

### Windows PowerShell:

```powershell
# 1. Ir a directorio del proyecto
cd C:\Users\Admn\Pictures\verduleria\app-android

# 2. Compilar APK Debug
.\gradlew assembleDebug

# 3. APK generado en:
# app\build\outputs\apk\debug\app-debug.apk
```

### Linux/Mac:

```bash
cd /ruta/app-android
./gradlew assembleDebug
```

## Opción 3: Usar APK Pre-compilado

Si no quieres compilar, puedo generar un APK firmado listo para instalar.

## Instalación en Tablet

### Método 1: USB
```powershell
# Habilita "Depuración USB" en tablet (Opciones de desarrollador)
# Conecta tablet por USB

# Instalar APK
adb install app-debug.apk
```

### Método 2: Transferencia directa
1. Copia `app-debug.apk` a tablet (USB, email, Drive, etc.)
2. En tablet: abre archivo APK
3. Acepta instalación de "Fuentes desconocidas"
4. Instalar

## Permisos en Primera Ejecución

Cuando abras la app por primera vez:

1. **Conecta báscula USB OTG**
2. Android mostrará: "¿Permitir acceso USB a Mr Verduli POS?"
3. **Marca:** "Usar siempre para este dispositivo USB"
4. **Click:** "OK"

## Verificación de Funcionalidad

1. Abre app
2. Click en ícono báscula (arriba derecha)
3. Debe mostrar "1 dispositivo encontrado"
4. Click "Conectar Báscula"
5. Peso debe aparecer en tiempo real

## Troubleshooting

### "Error compilando"
```powershell
# Limpiar y recompilar
.\gradlew clean
.\gradlew assembleDebug
```

### "APK no instala"
- Habilita "Instalación de apps desconocidas" en Android
- Configuración > Seguridad > Fuentes desconocidas

### "No detecta báscula"
- Verifica cable OTG funcional
- Prueba con otra app USB (USB Serial Terminal)
- Verifica VID:PID en configuración USB Android

### "Peso no se actualiza"
- Reinicia báscula (apaga/enciende)
- Desconecta y reconecta USB
- Verifica baudrate 2400 en báscula

## Siguiente Paso: Compilar

¿Quieres que te ayude con alguna de las opciones de compilación?
