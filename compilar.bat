@echo off
echo ============================================
echo   COMPILADOR RAPIDO - Mr Verduli POS
echo ============================================
echo.

REM Verificar si existe gradlew.bat
if not exist gradlew.bat (
    echo ERROR: gradlew.bat no encontrado
    echo Descarga Gradle desde https://gradle.org/releases/
    pause
    exit /b 1
)

echo [1/3] Limpiando proyecto...
call gradlew.bat clean

echo.
echo [2/3] Compilando APK Debug...
call gradlew.bat assembleDebug

echo.
echo [3/3] Verificando APK generado...

set APK_PATH=app\build\outputs\apk\debug\app-debug.apk

if exist "%APK_PATH%" (
    echo.
    echo ============================================
    echo   COMPILACION EXITOSA!
    echo ============================================
    echo.
    echo APK generado en:
    echo %CD%\%APK_PATH%
    echo.
    
    REM Obtener tamaño del APK
    for %%A in ("%APK_PATH%") do set APK_SIZE=%%~zA
    set /a APK_MB=%APK_SIZE%/1048576
    echo Tamaño: %APK_MB% MB
    echo.
    
    echo Siguiente paso:
    echo 1. Copia el APK a tu tablet
    echo 2. Abre el archivo en la tablet
    echo 3. Acepta instalacion
    echo 4. Conecta bascula USB OTG
    echo 5. Abre la app y otorga permisos USB
    echo.
    
    choice /c SN /m "Abrir carpeta del APK?"
    if errorlevel 2 goto END
    if errorlevel 1 explorer app\build\outputs\apk\debug
    
) else (
    echo.
    echo ============================================
    echo   ERROR EN COMPILACION
    echo ============================================
    echo.
    echo No se genero el APK. Revisa los errores arriba.
    echo.
)

:END
echo.
pause
