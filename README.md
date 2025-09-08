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