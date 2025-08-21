@echo off
echo Baixando e instalando SQL Server Express...

REM Download SQL Server Express
curl -L -o sqlserver.exe "https://go.microsoft.com/fwlink/p/?linkid=2216019"

REM Install silently
sqlserver.exe /ACTION=Install /FEATURES=SQLEngine /INSTANCENAME=SQLEXPRESS /SECURITYMODE=SQL /SAPWD=123456 /TCPENABLED=1 /IACCEPTSQLSERVERLICENSETERMS /QUIET

REM Start services
net start MSSQL$SQLEXPRESS
net start SQLBrowser

REM Enable TCP/IP
sqlcmd -S localhost\SQLEXPRESS -U sa -P 123456 -Q "EXEC sp_configure 'remote access', 1; RECONFIGURE;"

echo SQL Server instalado e configurado!
pause