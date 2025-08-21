@echo off
echo Verificando SQL Server...

echo.
echo 1. Verificando servicos SQL Server:
sc query MSSQLSERVER
sc query "SQL Server (SQLEXPRESS)"

echo.
echo 2. Verificando portas abertas:
netstat -an | findstr :1433

echo.
echo 3. Tentando conectar com sqlcmd:
sqlcmd -S localhost -U sa -P @ITB123456 -Q "SELECT @@VERSION"

pause