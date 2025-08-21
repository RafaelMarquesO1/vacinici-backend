# Instruções de Setup - SQL Server

## 1. Pré-requisitos
- SQL Server instalado e rodando
- Usuário `sa` com senha `123456`
- Porta 1433 disponível

## 2. Setup do Banco
Execute um dos comandos:

### Opção A: Script Automático
```bash
sql-server-check.bat
```

### Opção B: Manual
```bash
sqlcmd -S localhost -U sa -P 123456 -i VaciniciBD.sql
```

## 3. Executar Backend
```bash
run.bat
```

## 4. Credenciais de Teste
- **Funcionário:** maria.silva@ubs.gov.br / secret
- **Funcionário:** stephanie.santos@ubs.gov.br / secret
- **Paciente:** jamesmoraes@gmail.com / secret
- **Paciente:** ana.silva@email.com / secret

## 5. Senhas Criptografadas
Todas as senhas no banco estão com hash BCrypt: `$2a$10$N9qo8uLOickgx2ZMRZoMye/Ci/QcCmdqJ/avQ4Ens6slIMfhGxDWi`

Senha original para todos: **secret**