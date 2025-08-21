-- Script para testar senhas no banco
USE VaciniciBD;
GO

-- Verificar usuários cadastrados
SELECT id, nome_completo, email, tipo_usuario, cargo FROM usuarios;

-- Atualizar senhas com hash correto (senha: secret para todos)
UPDATE usuarios SET senha = '$2a$10$N9qo8uLOickgx2ZMRZoMye/Ci/QcCmdqJ/avQ4Ens6slIMfhGxDWi';

-- Verificar atualização
SELECT id, nome_completo, email, tipo_usuario, LEFT(senha, 20) + '...' as senha_hash FROM usuarios;