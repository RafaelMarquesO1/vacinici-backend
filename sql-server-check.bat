@echo off
echo ========================================
echo    VERIFICACAO SQL SERVER
echo ========================================
echo.

echo Verificando se o SQL Server esta rodando...
echo.

sqlcmd -S localhost -U sa -P 123456 -Q "SELECT @@VERSION" >nul 2>&1
if %errorlevel% equ 0 (
    echo ✓ SQL Server esta rodando!
    echo.
    echo Executando script VaciniciBD.sql...
    sqlcmd -S localhost -U sa -P 123456 -i VaciniciBD.sql
    if %errorlevel% equ 0 (
        echo ✓ Banco VaciniciBD criado com sucesso!
        echo ✓ Dados de teste inseridos!
    ) else (
        echo ✗ Erro ao executar script SQL!
    )
) else (
    echo ✗ SQL Server nao esta rodando ou credenciais incorretas!
    echo.
    echo Verifique:
    echo 1. SQL Server esta instalado e rodando
    echo 2. Credenciais: sa / 123456
    echo 3. Porta 1433 esta disponivel
    echo.
    echo Para instalar SQL Server Express:
    echo https://www.microsoft.com/sql-server/sql-server-downloads
)

echo.
pause