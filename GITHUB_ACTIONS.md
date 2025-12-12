# 🚀 COMPILACIÓN AUTOMÁTICA CON GITHUB ACTIONS

## ✅ **YO HAGO TODO EL SETUP - TÚ SOLO SUBES**

### 📋 **Pasos (Super fácil):**

#### **1. Crea cuenta GitHub (si no tienes)**
- Ve a: https://github.com/signup
- Email, contraseña, username
- Verifica email
- **GRATIS** ✅

#### **2. Crea repositorio nuevo**
```
1. Click botón verde "New repository"
2. Nombre: "verduleria-pos-android"
3. Privado o Público (tu eliges)
4. Click "Create repository"
```

#### **3. Sube el código (desde tu PC)**

**Opción A: Desde VS Code** (Más fácil)
```
1. En VS Code: Ctrl+Shift+P
2. Busca: "Git: Initialize Repository"
3. Selecciona carpeta app-android
4. Click en "Source Control" (ícono ramas)
5. Click "+" para agregar todos los archivos
6. Escribe mensaje: "Initial commit - POS Android App"
7. Click ✓ (commit)
8. Click "..." > Push to > GitHub
9. Autoriza VS Code en GitHub
10. Selecciona "verduleria-pos-android"
11. ¡Listo!
```

**Opción B: Desde Terminal**
```powershell
cd C:\Users\Admn\Pictures\verduleria\app-android

# Inicializar Git
git init
git add .
git commit -m "Initial commit - POS Android App"

# Conectar con GitHub (reemplaza TU_USUARIO)
git remote add origin https://github.com/TU_USUARIO/verduleria-pos-android.git
git branch -M main
git push -u origin main
```

#### **4. GitHub compila automáticamente** 🎉

Cuando hagas push, GitHub Actions:
1. ✅ Detecta el código
2. ✅ Instala JDK 17 automáticamente
3. ✅ Descarga dependencias
4. ✅ Compila APK
5. ✅ **Guarda APK listo para descargar**

**Tiempo total: 3-5 minutos**

---

## 📥 **Descargar APK compilado:**

1. Ve a tu repo en GitHub
2. Click pestaña **"Actions"**
3. Click en el build más reciente (verde ✅)
4. Scroll abajo: **"Artifacts"**
5. Click **"app-debug"** para descargar
6. Descomprime ZIP
7. **app-debug.apk** listo para instalar ✅

---

## 🔄 **Compilar nuevas versiones:**

Cuando hagas cambios al código:

```powershell
cd app-android
git add .
git commit -m "Actualización: [descripción del cambio]"
git push
```

GitHub compila automáticamente y genera nuevo APK ✅

---

## 🎯 **Ventajas de GitHub Actions:**

| Característica | GitHub Actions | PC Local |
|----------------|----------------|----------|
| **Instalación** | ❌ Nada | ✅ 150 MB - 1.4 GB |
| **Compilación** | ✅ Automática | ⚙️ Manual |
| **Tiempo** | 3-5 min | 5-10 min |
| **Costo** | 🆓 GRATIS | 🆓 GRATIS |
| **Historial** | ✅ Guardado 30 días | ❌ No |
| **Compartir** | ✅ Link directo | 📧 Enviar archivo |

---

## ⚡ **RESUMEN:**

**YO YA CREÉ:**
- ✅ `.github/workflows/build-apk.yml` (configuración automática)
- ✅ `.gitignore` (archivos a ignorar)
- ✅ Esta guía

**TÚ SOLO HACES:**
1. Crear cuenta GitHub (1 min)
2. Crear repositorio (30 seg)
3. Subir código (2 min desde VS Code)
4. **¡Esperar 3-5 min y descargar APK!** 🎉

---

## 🆘 **¿Necesitas ayuda?**

Si no tienes cuenta GitHub o no has usado Git antes, te guío paso a paso.
¿Quieres que te ayude a subirlo ahora?
