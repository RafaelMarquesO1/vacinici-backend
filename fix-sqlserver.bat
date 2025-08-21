@echo off
echo Configurando SQL Server para funcionar...

echo.
echo 1. Iniciando servicos SQL Server:
net start MSSQLSERVER 2>nul
net start "SQL Server (SQLEXPRESS)" 2>nul

echo.
echo 2. Habilitando TCP/IP e configurando porta 1433:
sqlcmd -S localhost -E -Q "EXEC sp_configure 'remote access', 1; RECONFIGURE;"

echo.
echo 3. Habilitando autenticacao mista:
sqlcmd -S localhost -E -Q "EXEC xp_instance_regwrite N'HKEY_LOCAL_MACHINE', N'Software\Microsoft\MSSQLServer\MSSQLServer', N'LoginMode', REG_DWORD, 2;"

echo.
echo 4. Configurando usuario sa:
sqlcmd -S localhost -E -Q "ALTER LOGIN sa ENABLE;"
sqlcmd -S localhost -E -Q "ALTER LOGIN sa WITH PASSWORD = '@ITB123456';"

echo.
echo 5. Criando banco VaciniciBD:
sqlcmd -S localhost -U sa -P @ITB123456 -Q "IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'VaciniciBD') CREATE DATABASE VaciniciBD;"

echo.
echo 6. Reiniciando SQL Server:
net stop MSSQLSERVER
net start MSSQLSERVER

echo.
echo SQL Server configurado! Teste a conexao com:
echo sqlcmd -S localhost -U sa -P @ITB123456 -Q "SELECT @@VERSION"
pause