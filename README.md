# Vacinici Backend

Backend unificado para o sistema de vacinação Vacinici, desenvolvido em Spring Boot.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Spring Security**
- **SQL Server** (banco de dados)
- **JWT** para autenticação
- **Maven** para gerenciamento de dependências

## Funcionalidades

### Autenticação
- Login com email e senha
- Registro de novos usuários
- Autenticação JWT

### Gestão de Usuários
- CRUD completo de usuários (Pacientes e Funcionários)
- Validação de dados
- Criptografia de senhas

### Gestão de Vacinas
- CRUD completo de vacinas
- Busca por categoria, nome e idade aplicável
- Informações detalhadas sobre cada vacina

### Locais de Vacinação
- CRUD completo de locais
- Busca por cidade, estado e tipo
- Informações de contato e localização

### Histórico de Vacinação
- Registro completo de vacinações
- Histórico por paciente
- Estatísticas de vacinação
- Validação de dados relacionais

## Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6 ou superior

### Executando a Aplicação

1. **Via Script (Windows):**
   ```bash
   run.bat
   ```

2. **Via Maven:**
   ```bash
   mvn clean spring-boot:run
   ```

3. **Via IDE:**
   - Importe o projeto como projeto Maven
   - Execute a classe `VaciniciBackendApplication`

### Acessando a Aplicação

- **API Base URL:** `http://localhost:8080/api`
- **SQL Server:** `localhost:1433`
  - Database: `VaciniciBD`
  - Username: `sa`
  - Password: `123456`

## Endpoints da API

### Autenticação
- `POST /auth/login` - Login
- `POST /auth/register` - Registro

### Usuários
- `GET /usuarios` - Listar todos os usuários
- `GET /usuarios/{id}` - Buscar usuário por ID
- `GET /usuarios/pacientes` - Listar pacientes
- `GET /usuarios/funcionarios` - Listar funcionários
- `POST /usuarios` - Criar usuário
- `PUT /usuarios/{id}` - Atualizar usuário
- `DELETE /usuarios/{id}` - Deletar usuário

### Vacinas
- `GET /vacinas` - Listar todas as vacinas
- `GET /vacinas/{id}` - Buscar vacina por ID
- `GET /vacinas/categoria/{categoria}` - Buscar por categoria
- `POST /vacinas` - Criar vacina
- `PUT /vacinas/{id}` - Atualizar vacina
- `DELETE /vacinas/{id}` - Deletar vacina

### Locais de Vacinação
- `GET /locais` - Listar todos os locais
- `GET /locais/{id}` - Buscar local por ID
- `GET /locais/cidade/{cidade}` - Buscar por cidade
- `POST /locais` - Criar local
- `PUT /locais/{id}` - Atualizar local
- `DELETE /locais/{id}` - Deletar local

### Histórico de Vacinação
- `GET /historico` - Listar todo o histórico
- `GET /historico/{id}` - Buscar histórico por ID
- `GET /historico/paciente/{pacienteId}` - Histórico por paciente
- `POST /historico` - Criar registro de vacinação
- `PUT /historico/{id}` - Atualizar registro
- `DELETE /historico/{id}` - Deletar registro

## Dados de Teste

A aplicação inicializa automaticamente com dados de teste:

### Usuários de Teste
- **Paciente:** jamesmoraes@gmail.com / james123456
- **Funcionário:** maria.silva@ubs.gov.br / admin123456

### Vacinas Disponíveis
- COVID-19 (Pfizer)
- Influenza (Butantan)
- Febre Amarela (Bio-Manguinhos)
- Tríplice Viral (GSK)
- BCG (Fundação Ataulpho de Paiva)
- Hepatite A (GSK)

### Locais de Vacinação
- UBS Central (São Paulo/SP)
- Hospital Municipal (Rio de Janeiro/RJ)
- Clínica Vacina Fácil (Belo Horizonte/MG)

## Configuração

As configurações principais estão no arquivo `application.properties`:

- Porta do servidor: 8080
- Banco de dados: H2 em memória
- JWT Secret: Configurável
- CORS: Habilitado para desenvolvimento

## Segurança

- Senhas criptografadas com BCrypt
- Autenticação JWT
- CORS configurado
- Validação de dados de entrada
- Proteção contra SQL Injection via JPA

## Integração com Frontend

Este backend está preparado para funcionar com:
- **React Web App** (porta 3000/5173)
- **Flutter Mobile App** (emulador Android e iOS)

As URLs de CORS estão configuradas para permitir requisições dos frontends.