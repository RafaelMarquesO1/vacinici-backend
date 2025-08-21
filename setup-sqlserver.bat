@echo off
echo Configurando SQL Server...

echo.
echo 1. Habilitando SQL Server Authentication...
sqlcmd -S localhost -E -Q "ALTER LOGIN sa ENABLE;"
sqlcmd -S localhost -E -Q "ALTER LOGIN sa WITH PASSWORD = 'SuaSenhaAqui123';"

echo.
echo 2. Criando banco de dados VaciniciBD...
sqlcmd -S localhost -E -Q "IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'VaciniciBD') CREATE DATABASE VaciniciBD;"

echo.
echo 3. Configurando Mixed Mode Authentication...
sqlcmd -S localhost -E -Q "EXEC xp_instance_regwrite N'HKEY_LOCAL_MACHINE', N'Software\Microsoft\MSSQLServer\MSSQLServer', N'LoginMode', REG_DWORD, 2;"

echo.
echo SQL Server configurado com sucesso!
echo Usuario: sa
echo Senha: SuaSenhaAqui123
echo Banco: VaciniciBD
echo.
echo IMPORTANTE: Reinicie o servico SQL Server para aplicar as mudancas:
echo net stop MSSQLSERVER
echo net start MSSQLSERVER
echo.
pause