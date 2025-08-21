@echo off
echo Iniciando SQL Server LocalDB...

REM Start LocalDB instance
sqllocaldb start MSSQLLocalDB

REM Create database
sqlcmd -S "(localdb)\MSSQLLocalDB" -Q "IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'VaciniciBD') CREATE DATABASE VaciniciBD;"

echo LocalDB iniciado e banco criado!
echo Agora execute run.bat
pause