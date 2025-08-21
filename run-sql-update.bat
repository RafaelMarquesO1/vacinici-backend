@echo off
echo Atualizando usuario admin no banco VaciniciBD...

sqlcmd -S localhost -U sa -P @ITB123456 -i update-admin-user.sql

echo.
echo Verificando usuarios no banco:
sqlcmd -S localhost -U sa -P @ITB123456 -d VaciniciBD -Q "SELECT id, nome_completo, email, tipo_usuario, cargo FROM usuarios WHERE tipo_usuario = 'Funcionario'"

pause