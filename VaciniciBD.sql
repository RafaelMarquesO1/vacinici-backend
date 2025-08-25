USE master;
GO

IF EXISTS (SELECT name FROM sys.databases WHERE name = 'VaciniciBD')
    DROP DATABASE VaciniciBD;
GO

CREATE DATABASE VaciniciBD;
GO

USE VaciniciBD;
GO

-- Tabela de Usuários
CREATE TABLE usuarios (
  id BIGINT IDENTITY(1,1) PRIMARY KEY,
  nome_completo NVARCHAR(100) NOT NULL,
  email NVARCHAR(100) UNIQUE,
  telefone NVARCHAR(20),
  cpf NVARCHAR(20) UNIQUE NOT NULL,
  data_nascimento DATE,
  genero NVARCHAR(20),
  tipo_usuario NVARCHAR(20) NOT NULL,
  cargo NVARCHAR(50),
  foto_perfil NVARCHAR(255),
  data_cadastro DATETIME2 DEFAULT GETDATE(),
  senha NVARCHAR(255) NOT NULL
);

-- Tabela de Vacinas
CREATE TABLE vacinas (
  id BIGINT IDENTITY(1,1) PRIMARY KEY,
  nome NVARCHAR(100) NOT NULL,
  fabricante NVARCHAR(100),
  descricao NVARCHAR(MAX),
  doses_recomendadas INT,
  intervalo_doses INT,
  idade_minima INT,
  idade_maxima INT,
  categoria NVARCHAR(50),
  imagem_url NVARCHAR(255)
);

-- Tabela de Locais de Vacinação
CREATE TABLE locais_vacinacao (
  id BIGINT IDENTITY(1,1) PRIMARY KEY,
  nome NVARCHAR(100) NOT NULL,
  endereco NVARCHAR(255) NOT NULL,
  cidade NVARCHAR(100) NOT NULL,
  estado NVARCHAR(2) NOT NULL,
  cep NVARCHAR(10),
  telefone NVARCHAR(20),
  horario_funcionamento NVARCHAR(100),
  latitude FLOAT,
  longitude FLOAT,
  tipo NVARCHAR(50)
);

-- Tabela de Histórico de Vacinação
CREATE TABLE historico_vacinacao (
  id BIGINT IDENTITY(1,1) PRIMARY KEY,
  paciente_id BIGINT NOT NULL,
  funcionario_id BIGINT NOT NULL,
  vacina_id BIGINT NOT NULL,
  dose NVARCHAR(50) NOT NULL,
  data_aplicacao DATE NOT NULL,
  lote NVARCHAR(50) NOT NULL,
  validade DATE,
  local_id BIGINT,
  comprovante_url NVARCHAR(255),
  observacoes NVARCHAR(MAX),
  FOREIGN KEY (paciente_id) REFERENCES usuarios(id) ON DELETE CASCADE,
  FOREIGN KEY (funcionario_id) REFERENCES usuarios(id),
  FOREIGN KEY (vacina_id) REFERENCES vacinas(id),
  FOREIGN KEY (local_id) REFERENCES locais_vacinacao(id)
);

-- Tabela de Agendamentos
CREATE TABLE agendamentos (
  id BIGINT IDENTITY(1,1) PRIMARY KEY,
  paciente_id BIGINT NOT NULL,
  vacina_id BIGINT NOT NULL,
  local_id BIGINT NOT NULL,
  data_agendamento DATETIME2 NOT NULL,
  status NVARCHAR(50) NOT NULL DEFAULT 'Agendado',
  data_criacao DATETIME2 DEFAULT GETDATE(),
  FOREIGN KEY (paciente_id) REFERENCES usuarios(id) ON DELETE CASCADE,
  FOREIGN KEY (vacina_id) REFERENCES vacinas(id),
  FOREIGN KEY (local_id) REFERENCES locais_vacinacao(id)
);

-- Dados de teste - Usuários
INSERT INTO usuarios (nome_completo, email, telefone, cpf, data_nascimento, genero, tipo_usuario, cargo, senha) VALUES
('Administrador Sistema', 'admin@vacinici.com', '11999999999', '000.000.000-00', '1990-01-01', 'Masculino', 'Funcionario', 'Administrador', 'admin123456'),
('James Moraes', 'jamesmoraes@gmail.com', '11987654321', '111.222.333-44', '1992-08-25', 'Masculino', 'Paciente', NULL, 'james123456'),
('Ana Carolina Silva', 'ana.silva@email.com', '11987654322', '222.333.444-55', '1990-05-15', 'Feminino', 'Paciente', NULL, 'ana123456'),
('Bruno Costa Lima', 'bruno.lima@email.com', '11987654323', '333.444.555-66', '1985-11-20', 'Masculino', 'Paciente', NULL, 'bruno123456'),
('Maria Silva', 'maria.silva@ubs.gov.br', '11987654324', '101.101.101-01', '1980-05-10', 'Feminino', 'Funcionario', 'Enfermeira', 'maria123456'),
('Stephanie Santos', 'stephanie.santos@ubs.gov.br', '11987654325', '102.102.102-02', '1985-09-15', 'Feminino', 'Funcionario', 'Enfermeira', 'stephanie123456');

-- Dados de teste - Vacinas
INSERT INTO vacinas (nome, fabricante, descricao, doses_recomendadas, intervalo_doses, idade_minima, categoria, imagem_url) VALUES
('COVID-19', 'Pfizer', 'Vacina contra o coronavírus SARS-CoV-2', 2, 21, 60, 'obrigatória', 'https://placehold.co/100x100/E0F2F1/2A9D8F?text=COVID'),
('Influenza', 'Butantan', 'Vacina contra Influenza', 1, NULL, 6, 'sazonal', 'https://placehold.co/100x100/E0F2F1/2A9D8F?text=GRIPE'),
('Febre Amarela', 'Bio-Manguinhos', 'Vacina contra Febre Amarela', 1, NULL, 9, 'obrigatória', 'https://placehold.co/100x100/E0F2F1/2A9D8F?text=FA'),
('Tríplice Viral', 'GSK', 'Vacina contra Sarampo, Caxumba e Rubéola', 2, 30, 12, 'obrigatória', 'https://placehold.co/100x100/E0F2F1/2A9D8F?text=MMR'),
('BCG', 'Fundação Ataulpho de Paiva', 'Previne formas graves de Tuberculose', 1, NULL, 0, 'obrigatória', 'https://placehold.co/100x100/E0F2F1/2A9D8F?text=BCG'),
('Hepatite A', 'GSK', 'Vacina contra Hepatite A', 1, NULL, 12, 'obrigatória', 'https://placehold.co/100x100/E0F2F1/2A9D8F?text=HEPA');

-- Dados de teste - Locais
INSERT INTO locais_vacinacao (nome, endereco, cidade, estado, cep, telefone, horario_funcionamento, latitude, longitude, tipo) VALUES
('UBS Central', 'Av. Principal, 123', 'São Paulo', 'SP', '01001-000', '1134567890', 'Segunda a Sexta, 8h às 17h', -23.550520, -46.633308, 'posto de saúde'),
('Hospital Municipal', 'Rua da Saúde, 456', 'Rio de Janeiro', 'RJ', '20001-000', '2123456789', 'Todos os dias, 24h', -22.906847, -43.172896, 'hospital'),
('Clínica Vacina Fácil', 'Rua Comercial, 789', 'Belo Horizonte', 'MG', '30001-000', '3134567890', 'Segunda a Sábado, 8h às 18h', -19.919054, -43.938560, 'clínica');

-- Dados de teste - Histórico
INSERT INTO historico_vacinacao (paciente_id, funcionario_id, vacina_id, dose, data_aplicacao, lote, validade, local_id, observacoes) VALUES
(1, 4, 5, 'Dose Única', '1992-08-25', 'BCG92A', NULL, 1, 'Aplicação ao nascer'),
(1, 4, 3, 'Dose Única', '2010-03-15', 'FA10B', '2025-03-15', 2, NULL),
(1, 5, 2, 'Dose Anual', '2024-04-22', 'FLU24C', '2024-12-31', 2, 'Campanha 2024'),
(1, 4, 1, '1ª Dose', '2022-01-18', 'COV22D', '2024-01-18', 1, NULL),
(1, 4, 1, '2ª Dose', '2022-04-18', 'COV22E', '2024-04-18', 1, 'Esquema completo'),
(1, 5, 6, 'Dose Única', '1993-11-05', 'HEPA93F', '2025-11-05', 3, NULL);

INSERT INTO historico_vacinacao (paciente_id, funcionario_id, vacina_id, dose, data_aplicacao, lote, validade, local_id, observacoes) VALUES
(2, 4, 5, 'Dose Única', '1992-08-25', 'BCG92A', NULL, 1, 'Aplicação ao nascer'),
(2, 4, 3, 'Dose Única', '2010-03-15', 'FA10B', '2025-03-15', 2, NULL),
(2, 5, 2, 'Dose Anual', '2024-04-22', 'FLU24C', '2024-12-31', 2, 'Campanha 2024'),
(2, 4, 1, '2ª Dose', '2022-04-18', 'COV22E', '2024-04-18', 1, 'Esquema completo');



-- Dados de teste - Agendamentos
INSERT INTO agendamentos (paciente_id, vacina_id, local_id, data_agendamento, status)
VALUES (2, 1, 1, '2025-09-01T09:00:00', 'Agendado'),
       (3, 2, 2, '2025-09-02T10:30:00', 'Agendado'),
       (4, 3, 3, '2025-09-03T14:00:00', 'Agendado');

GO


SELECT * FROM usuarios;
SELECT * FROM agendamentos;