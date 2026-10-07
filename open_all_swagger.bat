@echo off
REM ************************************************************
REM OPEN_ALL_SWAGGER.BAT - Configuración de Servicios de Microservicios
REM Objetivo: Abre todas las UIs Swagger configuradas en pestañas separadas.
REM Instrucciones: 1. Modificar la sección ":: *** CONFIGURACIÓN DE ENDPOINTS ***"
REM                2. Ejecutar este archivo .bat en Windows.
REM ************************************************************

echo =========================================================
echo Iniciando apertura de UIs Swagger...
echo =========================================================

:: --------------------------------------------------------
:: !!! === CONFIGURACIÓN DE ENDPOINTS === !!!
:: ¡IMPORTANTE! Debes modificar las URLs aquí. Cada servicio debe ser una línea 'start "" "URL"'
:: El formato es: start "" "http://localhost:PORT/swagger-ui.html"
:: --------------------------------------------------------

start "" "http://localhost:8080/swagger-ui/index.html"
start "" "http://localhost:8081/swagger-ui/index.html"
start "" "http://localhost:8082/swagger-ui/index.html"
start "" "http://localhost:8083/swagger-ui/index.html"

:: Si agregas un nuevo servicio, simplemente añade una línea 'start "" "nueva_url"' aquí.
:: --------------------------------------------------------



echo.
echo =========================================================
echo Éxito: Se intentó abrir la documentación de los servicios listados.
REM El comando 'pause' ha sido eliminado para que el script cierre automáticamente.
=========================================================