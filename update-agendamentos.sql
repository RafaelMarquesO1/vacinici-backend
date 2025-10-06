-- Script para adicionar coluna de motivo de cancelamento na tabela agendamentos
-- Execute este script no banco de dados existente

ALTER TABLE agendamentos 
ADD motivo_cancelamento NVARCHAR(255);

-- Comentário da coluna
EXEC sp_addextendedproperty 
    @name = N'MS_Description', 
    @value = N'Motivo do cancelamento do agendamento', 
    @level0type = N'SCHEMA', @level0name = N'dbo', 
    @level1type = N'TABLE', @level1name = N'agendamentos', 
    @level2type = N'COLUMN', @level2name = N'motivo_cancelamento';

