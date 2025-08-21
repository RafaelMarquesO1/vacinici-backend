@echo off
echo ========================================
echo    SETUP BACKEND VACINICI
echo ========================================
echo.

echo Verificando se o Java esta instalado...
java -version
if %errorlevel% neq 0 (
    echo ERRO: Java nao encontrado!
    echo Por favor, instale o Java 17 ou superior.
    echo Download: https://adoptium.net/
    pause
    exit /b 1
)

echo.
echo Verificando se o Maven esta instalado...
mvn -version
if %errorlevel% neq 0 (
    echo AVISO: Maven nao encontrado!
    echo.
    echo Opcoes:
    echo 1. Instalar Maven: https://maven.apache.org/download.cgi
    echo 2. Usar Maven Wrapper (incluido no projeto)
    echo.
    echo Criando Maven Wrapper...
    echo.
    
    REM Criar estrutura do Maven Wrapper
    mkdir .mvn\wrapper 2>nul
    
    echo @echo off > mvnw.cmd
    echo set MAVEN_PROJECTBASEDIR=%%~dp0 >> mvnw.cmd
    echo if not "%%MAVEN_PROJECTBASEDIR%%"=="" goto endDetectBaseDir >> mvnw.cmd
    echo set MAVEN_PROJECTBASEDIR=%%cd%% >> mvnw.cmd
    echo :endDetectBaseDir >> mvnw.cmd
    echo java -jar .mvn\wrapper\maven-wrapper.jar %%* >> mvnw.cmd
    
    echo Maven Wrapper criado! Use 'mvnw.cmd' ao inves de 'mvn'
    echo.
)

echo.
echo ========================================
echo Backend configurado com sucesso!
echo ========================================
echo.
echo Para executar:
echo   - Se tem Maven: mvn spring-boot:run
echo   - Se nao tem Maven: mvnw.cmd spring-boot:run
echo   - Ou execute: run.bat
echo.
echo IMPORTANTE: 
echo 1. Certifique-se que o SQL Server esta rodando!
echo 2. Execute sql-server-check.bat para criar o banco
echo 3. Ou execute VaciniciBD.sql manualmente
echo.
echo Acesse: http://localhost:8080/api
echo.
pause