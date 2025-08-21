USE VaciniciBD;

-- Verificar se o usuário admin já existe
IF EXISTS (SELECT 1 FROM usuarios WHERE email = 'admin@vacinici.com')
BEGIN
    -- Atualizar usuário existente
    UPDATE usuarios 
    SET senha = '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.'  -- admin123456 criptografado
    WHERE email = 'admin@vacinici.com';
    PRINT 'Usuario admin atualizado com sucesso!';
END
ELSE
BEGIN
    -- Inserir novo usuário admin
    INSERT INTO usuarios (nome_completo, email, telefone, cpf, data_nascimento, genero, tipo_usuario, cargo, senha)
    VALUES (
        'Administrador Sistema',
        'admin@vacinici.com', 
        '11999999999',
        '000.000.000-00',
        '1990-01-01',
        'Masculino',
        'Funcionario',
        'Administrador',
        '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.'  -- admin123456 criptografado
    );
    PRINT 'Usuario admin criado com sucesso!';
END

-- Verificar se foi criado/atualizado
SELECT id, nome_completo, email, tipo_usuario, cargo 
FROM usuarios 
WHERE email = 'admin@vacinici.com';