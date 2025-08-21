package com.vacinici.backend.repository;

import com.vacinici.backend.entity.HistoricoVacinacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HistoricoVacinacaoRepository extends JpaRepository<HistoricoVacinacao, Long> {
    List<HistoricoVacinacao> findByPacienteId(Long pacienteId);
    List<HistoricoVacinacao> findByFuncionarioId(Long funcionarioId);
    List<HistoricoVacinacao> findByVacinaId(Long vacinaId);
    List<HistoricoVacinacao> findByLocalId(Long localId);
    
    @Query("SELECT h FROM HistoricoVacinacao h WHERE h.paciente.id = :pacienteId AND h.vacina.id = :vacinaId")
    List<HistoricoVacinacao> findByPacienteAndVacina(@Param("pacienteId") Long pacienteId, @Param("vacinaId") Long vacinaId);
    
    @Query("SELECT h FROM HistoricoVacinacao h WHERE h.dataAplicacao BETWEEN :dataInicio AND :dataFim")
    List<HistoricoVacinacao> findByDataAplicacaoBetween(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    @Query("SELECT h FROM HistoricoVacinacao h JOIN FETCH h.paciente JOIN FETCH h.vacina JOIN FETCH h.funcionario WHERE h.paciente.id = :pacienteId ORDER BY h.dataAplicacao DESC")
    List<HistoricoVacinacao> findHistoricoCompletoByPaciente(@Param("pacienteId") Long pacienteId);
    
    @Query("SELECT COUNT(h) FROM HistoricoVacinacao h WHERE h.vacina.id = :vacinaId")
    Long countByVacinaId(@Param("vacinaId") Long vacinaId);
}