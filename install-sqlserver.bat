@echo off
echo Instalando SQL Server Express...
echo.

REM Download SQL Server Express
powershell -Command "Invoke-WebRequest -Uri 'https://go.microsoft.com/fwlink/p/?linkid=2216019' -OutFile 'SQL2022-SSEI-Expr.exe'"

REM Install SQL Server Express
SQL2022-SSEI-Expr.exe /ACTION=Install /FEATURES=SQLEngine /INSTANCENAME=SQLEXPRESS /SECURITYMODE=SQL /SAPWD=123456 /TCPENABLED=1 /IACCEPTSQLSERVERLICENSETERMS

echo.
echo SQL Server instalado! Executando configuracao...

REM Enable TCP/IP
sqlcmd -S localhost\SQLEXPRESS -U sa -P 123456 -Q "EXEC sp_configure 'remote access', 1; RECONFIGURE;"

REM Create database
sqlcmd -S localhost\SQLEXPRESS -U sa -P 123456 -i VaciniciBD.sql

echo.
echo Pronto! SQL Server configurado.
pause