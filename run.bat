@echo off
echo ========================================
echo    BACKEND VACINICI - INICIANDO
echo ========================================
echo.

echo Verificando SQL Server...
sqlcmd -S localhost -U sa -P 123456 -Q "SELECT 1" >nul 2>&1
if %errorlevel% neq 0 (
    echo ERRO: SQL Server nao esta rodando!
    echo Execute sql-server-check.bat para verificar
    pause
    exit /b 1
)
echo SQL Server OK!
echo.

echo Verificando Maven...
mvn -version >nul 2>&1
if %errorlevel% equ 0 (
    echo Maven encontrado! Executando com Maven...
    echo.
    mvn clean spring-boot:run
) else (
    echo Maven nao encontrado. Tentando Maven Wrapper...
    if exist mvnw.cmd (
        echo Maven Wrapper encontrado! Executando...
        echo.
        mvnw.cmd clean spring-boot:run
    ) else (
        echo ERRO: Nem Maven nem Maven Wrapper encontrados!
        echo.
        echo Por favor:
        echo 1. Instale o Maven: https://maven.apache.org/download.cgi
        echo 2. Ou execute setup.bat primeiro
        echo.
        pause
        exit /b 1
    )
)

echo.
echo ========================================
echo Backend finalizado!
echo ========================================
pause